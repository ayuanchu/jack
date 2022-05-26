package com.kancy.mybatisplus.generator.service;

import com.kancy.mybatisplus.generator.config.DataSourceConfig;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * DatabaseService
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/9 19:45
 **/

public interface DatabaseService {
    /**
     * 获取查询数据列表的SQL
     * @param dataSourceConfig
     * @return
     */
    boolean testConnect(DataSourceConfig dataSourceConfig);

    /**
     * 获取查询数据列表的SQL
     * @param columnInfo
     * @return
     */
    String toJavaDataType(ColumnInfo columnInfo);

    /**
     * 判断是否为主键字段
     * @param columnInfo
     * @return
     */
    boolean isPrimaryKey(ColumnInfo columnInfo);

    /**
     * 判断是否为内置数据库
     * @param dataSourceConfig
     * @return
     */
    boolean isBuiltInDatabase(DataSourceConfig dataSourceConfig);

    /**
     * 获取数据库名称列表
     * @param dataSourceConfig
     * @return
     */
    List<String> getDatabaseNames(DataSourceConfig dataSourceConfig);

    /**
     * 获取查询数据库表列表的SQL
     * @param dataSourceConfig
     * @param databaseName
     * @return
     */
    List<TableInfo> getTables(DataSourceConfig dataSourceConfig, String databaseName);

    /**
     * 获取查询数据库表表信息
     * @param dataSourceConfig
     * @param databaseName
     * @param tableName
     * @return
     */
    TableInfo getTable(DataSourceConfig dataSourceConfig, String databaseName, String tableName);

    /**
     * 获取查询表字段的列表SQL
     * @param dataSourceConfig
     * @param databaseName
     * @param tableName
     * @return
     */
    List<ColumnInfo> getColumns(DataSourceConfig dataSourceConfig, String databaseName, String tableName);

    /**
     * 获取查询表字段的列表SQL
     * @param dataSourceConfig
     * @param databaseName
     * @param tableNames
     * @return
     */
    Map<String, List<ColumnInfo>> getColumns(DataSourceConfig dataSourceConfig, String databaseName, Set<String> tableNames);
}
