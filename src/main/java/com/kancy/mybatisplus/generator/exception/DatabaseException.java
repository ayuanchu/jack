package com.kancy.mybatisplus.generator.exception;

/**
 * DatabaseConnectionException
 *
 * @author kancy
 * @date 2020/6/13 17:15
 */
public class DatabaseException extends AlertException {
    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
