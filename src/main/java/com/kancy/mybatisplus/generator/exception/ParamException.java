package com.kancy.mybatisplus.generator.exception;

/**
 * <p>
 * ParamException
 * <p>
 *
 * @author: kancy
 * @date: 2020/6/11 13:19
 **/

public class ParamException extends AlertException {

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public ParamException(String message) {
        super(message);
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.
     * @since 1.4
     */
    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
