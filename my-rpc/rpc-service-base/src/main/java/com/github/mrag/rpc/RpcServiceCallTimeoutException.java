package com.github.mrag.rpc;

/**
 * 远程服务访问超时
 */
public class RpcServiceCallTimeoutException extends RuntimeException {

    public RpcServiceCallTimeoutException() {
    }

    public RpcServiceCallTimeoutException(String message) {
        super(message);
    }

    public RpcServiceCallTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcServiceCallTimeoutException(Throwable cause) {
        super(cause);
    }

    public RpcServiceCallTimeoutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
