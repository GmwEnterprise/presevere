package com.github.mrag.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 消费者服务注入的真实代理对象，由该类发起真实调用，请求远端服务
 */
public class ConsumerRemoteProxy<T> implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(ConsumerRemoteProxy.class);

    private final Class<T> proxyInterface;

    public ConsumerRemoteProxy(Class<T> proxyInterface) {
        this.proxyInterface = proxyInterface;
    }

    public T getProxy() {
        return ((T) Proxy.newProxyInstance(
                proxyInterface.getClassLoader(),
                new Class[]{proxyInterface},
                this));
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("调用方法: {}", method.getName());
        return null;
    }
}
