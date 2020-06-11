package com.github.mrag.wechat.common;

public class MessageTooShortException extends RuntimeException {

    public MessageTooShortException() {
    }

    public MessageTooShortException(String message) {
        super(message);
    }

    public MessageTooShortException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageTooShortException(Throwable cause) {
        super(cause);
    }

    public MessageTooShortException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
