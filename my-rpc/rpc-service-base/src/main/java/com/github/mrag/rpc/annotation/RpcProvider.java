package com.github.mrag.rpc.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcProvider {

    /**
     * 服务名称
     */
    @AliasFor("name")
    String value() default "";

    /**
     * 服务名称
     */
    @AliasFor("value")
    String name() default "";

    /**
     * 服务组
     */
    String group() default "DEFAULT-RPC-GROUP";

    /**
     * 版本号
     */
    String version() default "1.0.0";
}
