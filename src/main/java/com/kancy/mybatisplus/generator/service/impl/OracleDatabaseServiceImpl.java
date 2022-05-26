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

public class OracleDatabaseServiceImpl extends AbstractDatabaseService {

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
        executeSQL(dataSourceConfig, "select 1 from dual", (rowIndex, rs) -> true);
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
        Map<String, String> map = GlobalConfigManager.get().getDataTypeMappings().get(DatabaseDriverEnum.Oracle);
        String jdbcDtaType = columnInfo.getDataType();
        if (StringUtils.equalsIgnoreCase("NUMBER", jdbcDtaType)){
            if (Objects.nonNull(map)){
                String number = map.get("NUMBER");
                if (!StringUtils.isEmpty(number)){
                    return number;
                }
            }
            if (columnInfo.getNumericScale() > 0){
                return Double.class.getName();
            }
            if (columnInfo.getNumericPrecision() > 11 || columnInfo.getNumericPrecision() == 0){
                return Long.class.getName();
            }else {
                return Integer.class.getName();
            }
        }

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
        String columnKey = columnInfo.getColumnKey();
        if (StringUtils.isEmpty(columnKey)){
            return false;
        }
        return columnKey.startsWith("SYS_");
    }

    /**
     * 判断是否为内置数据库
     *
     * @param dataSourceConfig
     * @return
     */
    @Override
    public boolean isBuiltInDatabase(DataSourceConfig dataSourceConfig) {
        return false;
    }

    /**
     * 获取查询数据列表的SQL
     *
     * @return
     */
    @Override
    public String getDatabasesSql() {
        return "select 1 from dual";
    }

    /**
     * 获取查询数据列表的SQL
     *
     * @param dataSourceConfig
     * @return
     */
    @Override
    public List<String> getDatabaseNames(DataSourceConfig dataSourceConfig) {
        List<String> list = new ArrayList<>();
        if (Objects.nonNull(dataSourceConfig) && !StringUtils.isEmpty(dataSourceConfig.getUsername())){
            list.add(dataSourceConfig.getUsername());
        }
        return list;
    }


    /**
     * 获取查询数据库表列表的SQL
     *
     * @param databaseName
     * @return
     */
    @Override
    public String getTablesSql(String databaseName) {
        return "select " +
                    "t.TABLE_NAME as table_name," +
                    "o.CREATED as create_time," +
                    "'引擎' as engine," +
                    "c.COMMENTS as table_comment " +
                "from user_tables t " +
                "left join user_tab_comments c on c.TABLE_NAME = t.TABLE_NAME " +
                "left join user_objects o on o.OBJECT_NAME = t.TABLE_NAME and o.OBJECT_TYPE = 'TABLE'";
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
                "select " +
                    "t.TABLE_NAME as table_name," +
                    "o.CREATED as create_time," +
                    "'引擎' as engine," +
                    "c.COMMENTS as table_comment " +
                "from user_tables t " +
                "left join user_tab_comments c on c.TABLE_NAME = t.TABLE_NAME " +
                "left join user_objects o on o.OBJECT_NAME = t.TABLE_NAME and o.OBJECT_TYPE = 'TABLE' " + 
                "where t.TABLE_NAME = '%s'", tableName);
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
                "select " +
                    "t.COLUMN_NAME as column_name," +
                    "c.COMMENTS as column_comment," +
                    "t.DATA_TYPE as data_type," +
                    "i.INDEX_NAME as column_key," +
                    "t.DATA_TYPE as column_type," +
                    "t.DATA_DEFAULT as column_default," +
                    "t.NULLABLE as is_nullable, " +
                    "t.CHAR_LENGTH as char_length, " +
                    "t.DATA_PRECISION as numeric_precision, " +
                    "t.DATA_SCALE as numeric_scale " +
                "from user_tab_columns t " +
                "left join USER_IND_COLUMNS i on t.TABLE_NAME = i.TABLE_NAME and t.COLUMN_NAME = i.COLUMN_NAME " +
                "left join USER_COL_COMMENTS c on t.TABLE_NAME = c.TABLE_NAME and t.COLUMN_NAME = c.COLUMN_NAME " +
                "where t.TABLE_NAME = '%s'", tableName);
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
        OracleRowHandler rowHandler = new OracleRowHandler(columns);
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
                OracleRowHandler rowHandler = new OracleRowHandler(columns);
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
    static class OracleRowHandler implements RowHandler {

        private final List<ColumnInfo> columns;

        private String databaseName;

        private String tableName;

        public OracleRowHandler(List<ColumnInfo> columns) {
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
                    .setColumnName(rs.getString("column_name"))
                    .setColumnComment(rs.getString("column_comment"))
                    .setColumnDefault(rs.getString("column_default"))
                    .setColumnType(rs.getString("column_type"))
                    .setColumnKey(rs.getString("column_key"))
                    .setIsNullable(rs.getString("is_nullable"))
                    .setDataType(rs.getString("data_type"))
                    .setNumericPrecision(rs.getInt("numeric_precision"))
                    .setNumericScale(rs.getInt("numeric_scale"));

            if (tableInfo.getNumericPrecision() > 0){
                tableInfo.setColumnType(String.format("%s(%s,%s)", tableInfo.getDataType(),
                        tableInfo.getNumericPrecision(), tableInfo.getNumericScale()));
            }else {
                int char_length = rs.getInt("char_length");
                if (char_length > 0){
                    tableInfo.setColumnType(String.format("%s(%s)", tableInfo.getDataType(), char_length));
                }
            }

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
        builtInDatabaseNames.add("SCOTT");
        builtInDatabaseNames.add("SYSTEM");
        builtInDatabaseNames.add("SYS");

        // 初始化类型映射关系
        javaDataTypeMapping.put("CHAR", String.class);
        javaDataTypeMapping.put("VARCHAR2", String.class);
        javaDataTypeMapping.put("LONG VARCHAR", String.class);
        javaDataTypeMapping.put("VARCHAR", String.class);
        javaDataTypeMapping.put("NUMERIC", BigDecimal.class);
        javaDataTypeMapping.put("DECIMAL", BigDecimal.class);
        javaDataTypeMapping.put("INT", Integer.class);
        javaDataTypeMapping.put("TINYINT", Integer.class);
        javaDataTypeMapping.put("SMALLINT", Integer.class);
        javaDataTypeMapping.put("INTEGER", Integer.class);
        javaDataTypeMapping.put("BIGINT", Integer.class);
        javaDataTypeMapping.put("NUMBER", Double.class);
        javaDataTypeMapping.put("FLOAT", Double.class);
        javaDataTypeMapping.put("DOUBLE", Double.class);
        javaDataTypeMapping.put("TIMESTAMP", Date.class);
        javaDataTypeMapping.put("DATE", Date.class);


    }
}
