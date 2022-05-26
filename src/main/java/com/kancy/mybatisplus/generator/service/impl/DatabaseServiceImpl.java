package com.kancy.mybatisplus.generator.service.impl;

import com.kancy.mybatisplus.generator.config.DataSourceConfig;
import com.kancy.mybatisplus.generator.enums.DatabaseDriverEnum;
import com.kancy.mybatisplus.generator.manager.DataSourceManager;
import com.kancy.mybatisplus.generator.service.ColumnInfo;
import com.kancy.mybatisplus.generator.service.DatabaseService;
import com.kancy.mybatisplus.generator.service.TableInfo;

import java.util.*;

/**
 * <p>
 * DatabaseServiceImpl
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/10 10:37
 **/

public class DatabaseServiceImpl implements DatabaseService {

    private static final DatabaseService emptyDatabaseService = new EmptyDatabaseServiceImpl();

    private static final Map<String, DatabaseService> DATABASE_SERVICE_HASH_MAP = new HashMap<>();

    /**
     * 获取查询数据列表的SQL
     *
     * @param dataSourceConfig
     * @return
     */
    @Override
    public boolean testConnect(DataSourceConfig dataSourceConfig) {
        return findDatabaseService(dataSourceConfig).testConnect(dataSourceConfig);
    }

    /**
     * 获取查询数据列表的SQL
     *
     * @param columnInfo
     * @return
     */
    @Override
    public String toJavaDataType(ColumnInfo columnInfo) {
        return findDatabaseService().toJavaDataType(columnInfo);
    }

    /**
     * 判断是否为主键字段
     *
     * @param columnInfo
     * @return
     */
    @Override
    public boolean isPrimaryKey(ColumnInfo columnInfo) {
        return findDatabaseService().isPrimaryKey(columnInfo);
    }

    /**
     * 判断是否为内置数据库
     *
     * @param dataSourceConfig
     * @return
     */
    @Override
    public boolean isBuiltInDatabase(DataSourceConfig dataSourceConfig) {
        return findDatabaseService(dataSourceConfig).isBuiltInDatabase(dataSourceConfig);
    }

    /**
     * 获取查询数据列表的SQL
     *
     * @param dataSourceConfig
     * @return
     */
    @Override
    public List<String> getDatabaseNames(DataSourceConfig dataSourceConfig) {
        return findDatabaseService(dataSourceConfig).getDatabaseNames(dataSourceConfig);
    }

    /**
     * 获取查询数据列表的SQL
     *
     * @return
     */
    public List<String> getDatabaseNames() {
        DataSourceConfig dataSourceConfig = DataSourceManager.getActive();
        return findDatabaseService(dataSourceConfig).getDatabaseNames(dataSourceConfig);
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
        return findDatabaseService(dataSourceConfig).getTables(dataSourceConfig, databaseName);
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
        return findDatabaseService(dataSourceConfig).getTable(dataSourceConfig, databaseName, tableName);
    }
    /**
     * 获取查询数据库表表信息
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    public TableInfo getTable(String databaseName, String tableName) {
        DataSourceConfig dataSourceConfig = DataSourceManager.getActive();
        return findDatabaseService(dataSourceConfig).getTable(dataSourceConfig, databaseName, tableName);
    }

    /**
     * 获取查询数据库表列表的SQL
     *
     * @param databaseName
     * @return
     */
    public List<TableInfo> getTables( String databaseName) {
        DataSourceConfig dataSourceConfig = DataSourceManager.getActive();
        return findDatabaseService(dataSourceConfig).getTables(dataSourceConfig, databaseName);
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
        return findDatabaseService(dataSourceConfig).getColumns(dataSourceConfig, databaseName,tableName);
    }

    /**
     * 获取查询表字段的列表SQL
     *
     * @param databaseName
     * @param tableName
     * @return
     */
    public List<ColumnInfo> getColumns(String databaseName, String tableName) {
        DataSourceConfig dataSourceConfig = DataSourceManager.getActive();
        return findDatabaseService(dataSourceConfig).getColumns(dataSourceConfig, databaseName,tableName);
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
        return findDatabaseService(dataSourceConfig).getColumns(dataSourceConfig, databaseName, tableNames);
    }
    /**
     * 获取查询表字段的列表SQL
     *
     * @param databaseName
     * @param tableNames
     * @return
     */
    public Map<String, List<ColumnInfo>> getColumns(String databaseName, Set<String> tableNames) {
        DataSourceConfig dataSourceConfig = DataSourceManager.getActive();
        return findDatabaseService(dataSourceConfig).getColumns(dataSourceConfig, databaseName, tableNames);
    }

    private static DatabaseService findDatabaseService() {
        return findDatabaseService(DataSourceManager.getActive());
    }

    private static DatabaseService findDatabaseService(DataSourceConfig dataSourceConfig) {
        if (Objects.isNull(dataSourceConfig)){
            return emptyDatabaseService;
        }
        return findDatabaseService(dataSourceConfig.getDriver());
    }

    private static DatabaseService findDatabaseService(String driver) {
        return DATABASE_SERVICE_HASH_MAP.getOrDefault(driver, emptyDatabaseService);
    }

    static {
        DATABASE_SERVICE_HASH_MAP.put(DatabaseDriverEnum.MySQL.name(), new MySqlDatabaseServiceImpl());
        DATABASE_SERVICE_HASH_MAP.put(DatabaseDriverEnum.Oracle.name(), new OracleDatabaseServiceImpl());
    }
}
