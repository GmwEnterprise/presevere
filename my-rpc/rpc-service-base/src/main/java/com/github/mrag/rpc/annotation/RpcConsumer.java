package com.github.mrag.rpc.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcConsumer {

    /**
     * 服务名称
     */
    String name() default "";

    /**
     * 服务组
     */
    String group() default "DEFAULT-RPC-GROUP";

    /**
     * 版本号
     */
    String version() default "1.0.0";

    /**
     * 超时限制，单位为秒. 默认十秒
     */
    int timeout() default 10;
}
