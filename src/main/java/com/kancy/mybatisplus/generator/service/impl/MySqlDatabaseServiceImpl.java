package com.kancy.mybatisplus.generator.service.impl;

import com.kancy.mybatisplus.generator.config.DataSourceConfig;
import com.kancy.mybatisplus.generator.enums.DatabaseDriverEnum;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.manager.GlobalConfigManager;
import com.kancy.mybatisplus.generator.service.ColumnInfo;
import com.kancy.mybatisplus.generator.service.TableInfo;
import com.kancy.mybatisplus.generator.utils.JdbcUtils;
import com.kancy.mybatisplus.generator.utils.StringUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * <p>
 * MySqlDatabaseServiceImpl
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/9 19:46
 **/

public class MySqlDatabaseServiceImpl extends AbstractDatabaseService {

    /**
     * 内置数据库名
     */
    private static final List<String> builtInDatabaseNames = new ArrayList<>();

    /**
     * 数据库与JAVA类型映射
     */
    private static final Map<String, Class> javaDataTypeMapping = new HashMap<>();

    /**
     * 获取查询数据列表的SQL
     *
     * @return
     */
    @Override
    public boolean testConnect(DataSourceConfig dataSourceConfig) {
        executeSQL(dataSourceConfig, "select 1", (rowIndex, rs) -> true);
        Log.debug("test connect success : %s" , dataSourceConfig.showInfo());
        return true;
    }

    /**
     * 获取查询数据列表的SQL
     *
     * @param columnInfo
     * @return
     */
    @Override
    public String toJavaDataType(ColumnInfo columnInfo) {
        String jdbcDtaType = columnInfo.getDataType();
        Map<String, String> map = GlobalConfigManager.get().getDataTypeMappings().get(DatabaseDriverEnum.MySQL);
        if (Objects.nonNull(map)){
            return map.getOrDefault(jdbcDtaType, javaDataTypeMapping.getOrDefault(jdbcDtaType, Object.class).getName());
        }
        return javaDataTypeMapping.getOrDefault(jdbcDtaType, Object.class).getName();
    }

    /**
     * 判断是否为主键字段
     *
     * @param columnInfo
     * @return
     */
    @Override
    public boolean isPrimaryKey(ColumnInfo columnInfo) {
        return Objects.equals(columnInfo.getColumnKey(), "PRI");
    }

    /**
     * 判断是否为内置数据库
     *
     * @param dataSourceConfig
     * @return
     */
    @Override
    public boolean isBuiltInDatabase(DataSourceConfig dataSourceConfig) {
        return builtInDatabaseNames.contains(dataSourceConfig.getDatabase());
    }

    /**
     * 获取查询数据列表的SQL
     *
     * @return
     */
    @Override
    public String getDatabasesSql() {
        return "select schema_name from information_schema.schemata where schema_name not in ('information_schema','mysql','performance_schema','sys')";
    }

    /**
     * 获取查询数据列表的SQL
     *
     * @param dataSourceConfig
     * @return
     */
    @Override
    public List<String> getDatabaseNames(DataSourceConfig dataSourceConfig) {
        List<String> databaseNames = new ArrayList<>();
        executeSQL(dataSourceConfig, getDatabasesSql(), (rowIndex, rs) -> {
            databaseNames.add(rs.getString("schema_name"));
            return true;
        });
        return databaseNames;
    }


    /**
     * 获取查询数据库表列表的SQL
     *
     * @param databaseName
     * @return
     */
    @Override
    public String getTablesSql(String databaseName) {
        return String.format(
                "select table_name, create_time, engine, table_comment " +
                        "from information_schema.tables " +
                        "where table_schema = '%s' ", databaseName);
    }

    /**
     * 获取查询数据库表列表的SQL
     *
     * @param dataSourceConfig
     * @param databaseName
     * @return
     */
    @Override
    public List<TableInfo> getTables(DataSourceConfig dataSourceConfig, String databaseName) {
        if (StringUtils.isEmpty(databaseName)){
            return Collections.emptyList();
        }
        List<TableInfo> tables = new ArrayList<>();
        executeSQL(dataSourceConfig, getTablesSql(databaseName), (rowIndex, rs) -> {
            TableInfo tableInfo = new TableInfo()
                    .setDatabaseName("")
                    .setTableName(rs.getString("table_name"))
                    .setTableComment(rs.getString("table_comment"))
                    .setEngine(rs.getString("engine"))
                    .setCreateTime(rs.getString("create_time"));
            tables.add(tableInfo);
            return true;
        });
        return tables;
    }

    /**
     * 获取查询数据库表的SQL
     *
     * @param databaseName
     * @return
     */
    private String getTableSql(String databaseName, String tableName) {
        return String.format(
                "select table_name, create_time, engine, table_comment " +
                        "from information_schema.tables " +
                        "where table_schema = '%s' and table_name = '%s' ", databaseName, tableName);
    }

    /**
     * 获取查询数据库表表信息
     *
     * @param dataSourceConfig
     * @param databaseName
     * @param tableName
     * @return
     */
    @Override
    public TableInfo getTable(DataSourceConfig dataSourceConfig, String databaseName, String tableName) {
        if (Objects.isNull(dataSourceConfig) || StringUtils.isEmpty(databaseName)){
            return null;
        }
        List<TableInfo> tables = new ArrayList<>();
        executeSQL(dataSourceConfig, getTableSql(databaseName,tableName), (rowIndex, rs) -> {
            TableInfo tableInfo = new TableInfo()
                    .setDatabaseName("")
                    .setTableName(rs.getString("table_name"))
                    .setTableComment(rs.getString("table_comment"))
                    .setEngine(rs.getString("engine"))
                    .setCreateTime(rs.getString("create_time"));
            tables.add(tableInfo);
            return true;
        });
        if (tables.isEmpty()){
            return null;
        }
        return tables.get(0);
    }

    /**
     * 获取查询表字段的列表SQL
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    @Override
    public String getColumnsSql(String databaseName, String tableName) {
        return String.format(
                "select column_name,data_type,column_key,column_type,column_default,is_nullable,column_comment,numeric_precision,numeric_scale " +
                "from information_schema.columns " +
                "where table_schema = '%s' and table_name = '%s' order by ordinal_position", databaseName, tableName);
    }


    /**
     * 获取查询表字段的列表SQL
     *
     * @param dataSourceConfig
     * @param databaseName
     * @param tableName
     * @return
     */
    @Override
    public List<ColumnInfo> getColumns(DataSourceConfig dataSourceConfig, String databaseName, String tableName) {
        if (Objects.isNull(dataSourceConfig) || StringUtils.isEmpty(databaseName) || StringUtils.isEmpty(tableName)){
            return Collections.emptyList();
        }
        List<ColumnInfo> columns = new ArrayList<>();
        MysqlRowHandler rowHandler = new MysqlRowHandler(columns);
        rowHandler.setDatabaseName(databaseName);
        rowHandler.setTableName(tableName);
        executeSQL(dataSourceConfig, getColumnsSql(databaseName, tableName), rowHandler);
        return columns;
    }

    /**
     * 获取查询表字段的列表SQL
     *
     * @param dataSourceConfig
     * @param databaseName
     * @param tableNames
     * @return
     */
    @Override
    public Map<String, List<ColumnInfo>> getColumns(DataSourceConfig dataSourceConfig, String databaseName, Set<String> tableNames) {
        if (Objects.isNull(dataSourceConfig) || StringUtils.isEmpty(databaseName) || Objects.isNull(tableNames) || tableNames.isEmpty()){
            return Collections.emptyMap();
        }

        Map<String, List<ColumnInfo>> map = new HashMap<>();
        Connection connection = null;
        try {
            connection = getConnection(dataSourceConfig);
            for (String tableName : tableNames) {
                List<ColumnInfo> columns = new ArrayList<>();
                MysqlRowHandler rowHandler = new MysqlRowHandler(columns);
                rowHandler.setDatabaseName(databaseName);
                rowHandler.setTableName(tableName);
                executeSQL(connection, getColumnsSql(databaseName, tableName), rowHandler);
                map.put(tableName, columns);
            }
        } finally {
            JdbcUtils.closeConnection(connection);
        }
        return map;
    }


    /**
     * 数据行处理
     */
    static class MysqlRowHandler implements RowHandler {

        private final List<ColumnInfo> columns;

        private String databaseName;

        private String tableName;

        public MysqlRowHandler(List<ColumnInfo> columns) {
            this.columns = columns;
        }

        /**
         * 处理结果
         *
         * @param rowIndex
         * @param rs
         * @return
         * @throws SQLException
         */
        @Override
        public boolean handle(int rowIndex, ResultSet rs) throws SQLException {
            ColumnInfo tableInfo = new ColumnInfo()
                    .setDatabaseName(databaseName)
                    .setTableName(tableName)
                    .setNumericPrecision(rs.getInt("numeric_precision"))
                    .setNumericScale(rs.getInt("numeric_scale"))
                    .setColumnName(rs.getString("column_name"))
                    .setColumnComment(rs.getString("column_comment"))
                    .setColumnDefault(rs.getString("column_default"))
                    .setColumnType(rs.getString("column_type"))
                    .setColumnKey(rs.getString("column_key"))
                    .setIsNullable(rs.getString("is_nullable"))
                    .setDataType(rs.getString("data_type"));
            columns.add(tableInfo);
            return true;
        }

        public void setDatabaseName(String databaseName) {
            this.databaseName = databaseName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }
    }

    static {

        // 初始化内部数据库名
        builtInDatabaseNames.add("information_schema");
        builtInDatabaseNames.add("mysql");
        builtInDatabaseNames.add("performance_schema");
        builtInDatabaseNames.add("sys");

        // 初始化类型映射关系
        javaDataTypeMapping.put("char", String.class);
        javaDataTypeMapping.put("varchar", String.class);
        javaDataTypeMapping.put("text", String.class);
        javaDataTypeMapping.put("tinytext", String.class);
        javaDataTypeMapping.put("longtext", String.class);
        javaDataTypeMapping.put("int", Integer.class);
        javaDataTypeMapping.put("bit", Integer.class);
        javaDataTypeMapping.put("smallint", Integer.class);
        javaDataTypeMapping.put("tinyint", Integer.class);
        javaDataTypeMapping.put("bigint", Long.class);
        javaDataTypeMapping.put("double", Double.class);
        javaDataTypeMapping.put("float", Double.class);
        javaDataTypeMapping.put("decimal", BigDecimal.class);
        javaDataTypeMapping.put("numeric", Long.class);
        javaDataTypeMapping.put("timestamp", Date.class);
        javaDataTypeMapping.put("datetime", Date.class);
        javaDataTypeMapping.put("date", Date.class);
        javaDataTypeMapping.put("boolean", Boolean.class);

    }
}
