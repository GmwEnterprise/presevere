package cn.gmwenterprise.presevere.common;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthRequire {
    String[] value() default {};
}
