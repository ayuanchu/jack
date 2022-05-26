package com.kancy.mybatisplus.generator.exception;

/**
 * DatabaseConnectionException
 *
 * @author kancy
 * @date 2020/6/13 17:15
 */
public class DatabaseConnectionException extends DatabaseException {
    public DatabaseConnectionException(String message) {
        super(message);
    }

    public DatabaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
