package com.kancy.mybatisplus.generator.utils;

import com.kancy.mybatisplus.generator.exception.DatabaseException;
import com.kancy.mybatisplus.generator.log.Log;

import java.sql.*;
import java.util.Objects;

/**
 * <p>
 * JdbcUtils
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/12 15:16
 **/

public class JdbcUtils {
    /**
     * 执行SQL
     * @param connection
     * @param executeSQL
     * @param rowHandler
     */
    public static void executeSQL(Connection connection, String executeSQL, RowHandler rowHandler) {
        assert Objects.nonNull(connection);
        Statement stmt = null;
        try {
            Log.debug("执行SQL: %s", executeSQL);
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(executeSQL);
            int rowIndex = 0;
            boolean nextHandle = true;
            while (rs.next() && nextHandle) {
                nextHandle = rowHandler.handle(rowIndex, rs);
            }
        } catch (SQLException e) {
            throw new DatabaseException(String.format("操作数据源失败：%s", e.getMessage()), e);
        }finally {
            try {
                if (Objects.nonNull(stmt)){
                    stmt.close();
                }
            } catch (SQLException e) {
                throw new DatabaseException("关闭数据库失败", e);
            }
        }
    }

    /**
     * 获取连接
     * @param driverClassName
     * @param jdbcUrl
     * @param username
     * @param password
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static Connection getConnection(String driverClassName, String jdbcUrl, String username, String password)
            throws SQLException, ClassNotFoundException {
        Class.forName(driverClassName);
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public static void closeConnection(Connection connection) {
        if (Objects.nonNull(connection)){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DatabaseException("关闭数据库连接失败", e);
            }
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
