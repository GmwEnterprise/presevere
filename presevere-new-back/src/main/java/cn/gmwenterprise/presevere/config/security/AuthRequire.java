package cn.gmwenterprise.presevere.config.security;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthRequire {
    String[] value() default {};
}
