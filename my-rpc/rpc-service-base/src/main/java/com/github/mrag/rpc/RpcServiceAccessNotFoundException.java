package com.github.mrag.rpc;

/**
 * 远程服务未找到
 */
public class RpcServiceAccessNotFoundException extends RuntimeException {

    public RpcServiceAccessNotFoundException() {
    }

    public RpcServiceAccessNotFoundException(String message) {
        super(message);
    }

    public RpcServiceAccessNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcServiceAccessNotFoundException(Throwable cause) {
        super(cause);
    }

    public RpcServiceAccessNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
