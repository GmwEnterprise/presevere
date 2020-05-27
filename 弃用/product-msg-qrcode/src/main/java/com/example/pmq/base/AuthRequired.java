package com.example.pmq.base;

import org.springframework.stereotype.Indexed;

import java.lang.annotation.*;

/**
 * 需要验证权限
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Indexed
public @interface AuthRequired {
}
