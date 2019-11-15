package cn.gmwenterprise.presevere.config;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthRequire {
    /**
     * 定义在controller方法上，声明访问该接口所需要的权限
     * @return
     */
    String permission();
}
