package com.kancy.mybatisplus.generator.exception;

/**
 * TemplateException
 *
 * @author kancy
 * @date 2020/6/13 17:20
 */
public class CodeGeneratorException extends AlertException {

    public CodeGeneratorException(String message) {
        super(message);
    }

    public CodeGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
