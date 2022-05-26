package com.kancy.mybatisplus.generator.exception;

import java.util.Objects;

/**
 * AlertException
 *
 * @author kancy
 * @date 2020/6/13 17:00
 */
public class AlertException extends RuntimeException {
    private String alertMessage;
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public AlertException(String message) {
        super(message);
        this.alertMessage = message;
    }

    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval
     *                by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the
     *                {@link #getCause()} method).  (A <tt>null</tt> value is
     *                permitted, and indicates that the cause is nonexistent or
     *                unknown.)
     * @since 1.4
     */
    public AlertException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getAlertMessage() {
        if (Objects.isNull(alertMessage)){
            return getMessage();
        }
        return alertMessage;
    }

    public void setAlertMessage(String alertMessage) {
        this.alertMessage = alertMessage;
    }
}
