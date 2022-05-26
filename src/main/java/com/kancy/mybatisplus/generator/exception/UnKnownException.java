package com.kancy.mybatisplus.generator.exception;

/**
 * TemplateException
 *
 * @author kancy
 * @date 2020/6/13 17:20
 */
public class UnKnownException extends AlertException {

    public UnKnownException(String message) {
        super(message);
    }

    public UnKnownException(String message, Throwable cause) {
        super(message, cause);
    }
}
