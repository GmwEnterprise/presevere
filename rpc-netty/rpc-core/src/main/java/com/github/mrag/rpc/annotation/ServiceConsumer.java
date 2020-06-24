package com.github.mrag.rpc.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Gmw
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface ServiceConsumer {

    String name() default "";

    String group() default "DEFAULT_GROUP";

    String version() default "1.0.0-SNAPSHOT";
}
