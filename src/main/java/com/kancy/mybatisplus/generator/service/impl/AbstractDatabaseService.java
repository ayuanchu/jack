package com.kancy.mybatisplus.generator.service.impl;

import com.kancy.mybatisplus.generator.config.DataSourceConfig;
import com.kancy.mybatisplus.generator.exception.DatabaseConnectionException;
import com.kancy.mybatisplus.generator.exception.DatabaseException;
import com.kancy.mybatisplus.generator.service.DatabaseService;
import com.kancy.mybatisplus.generator.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * AbstractDatabaseService
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/9 19:45
 **/

public abstract class AbstractDatabaseService implements DatabaseService {

    /**
     * 获取查询数据列表的SQL
     * @return
     */
    abstract String getDatabasesSql();

    /**
     * 获取查询数据库表列表的SQL
     * @param databaseName
     * @return
     */
    abstract String getTablesSql(String databaseName);

    /**
     * 获取查询表字段的列表SQL
     * @param databaseName
     * @param tableName
     * @return
     */
    abstract String getColumnsSql(String databaseName, String tableName);

    /**
     * 执行SQL
     * @param connection
     * @param executeSQL
     * @param rowHandler
     */
    protected void executeSQL(Connection connection, String executeSQL, RowHandler rowHandler) {
        JdbcUtils.executeSQL(connection, executeSQL, (rowIndex, rs) -> rowHandler.handle(rowIndex, rs));
    }

    /**
     * 执行SQL
     * @param dataSourceConfig
     * @param executeSQL
     * @param rowHandler
     */
    protected void executeSQL(DataSourceConfig dataSourceConfig, String executeSQL, RowHandler rowHandler) {
        Connection connection = null;
        try {
            connection = getConnection(dataSourceConfig);
            JdbcUtils.executeSQL(connection, executeSQL, (rowIndex, rs) -> rowHandler.handle(rowIndex, rs));
        } finally{
            JdbcUtils.closeConnection(connection);
        }
    }

    /**
     * 获取连接
     * @param dataSourceConfig
     * @return
     */
    protected Connection getConnection(DataSourceConfig dataSourceConfig) {
        try {
             return JdbcUtils.getConnection(dataSourceConfig.getDriverClassName(), dataSourceConfig.getJdbcUrl(),
                    dataSourceConfig.getUsername(), dataSourceConfig.getPassword());
        } catch (SQLException e) {
            throw new DatabaseConnectionException("数据库连接失败！", e);
        } catch (ClassNotFoundException e) {
            throw new DatabaseException(String.format("数据库驱动类无效：%s", dataSourceConfig.getDriverClassName()), e);
        }
    }

    public interface RowHandler {
        /**
         * 处理结果
         * @param rs
         * @return
         * @throws SQLException
         */
        boolean handle(int rowIndex, ResultSet rs) throws SQLException;
    }

}
