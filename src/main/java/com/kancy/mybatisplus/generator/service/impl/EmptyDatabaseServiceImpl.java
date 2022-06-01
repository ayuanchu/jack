package com.kancy.mybatisplus.generator.service.impl;

import com.kancy.mybatisplus.generator.config.DataSourceConfig;
import com.kancy.mybatisplus.generator.service.ColumnInfo;
import com.kancy.mybatisplus.generator.service.DatabaseService;
import com.kancy.mybatisplus.generator.service.TableInfo;

import java.util.*;

/**
 * <p>
 * EmptyDatabaseServiceImpl
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/9 20:54
 **/

public class EmptyDatabaseServiceImpl implements DatabaseService {
    /**
     * 获取查询数据列表的SQL
     *
     * @return
     */
    @Override
    public boolean testConnect(DataSourceConfig dataSourceConfig) {
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
        return Object.class.getName();
    }

    /**
     * 判断是否为主键字段
     *
     * @param columnInfo
     * @return
     */
    @Override
    public boolean isPrimaryKey(ColumnInfo columnInfo) {
        return false;
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
     * @param dataSourceConfig
     * @return
     */
    @Override
    public List<String> getDatabaseNames(DataSourceConfig dataSourceConfig) {
        return Collections.emptyList();
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
        return Collections.emptyList();
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
        return null;
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
        return Collections.emptyList();
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
        return Collections.emptyMap();
    }
}
