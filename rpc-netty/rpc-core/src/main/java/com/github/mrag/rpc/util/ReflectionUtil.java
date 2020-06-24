package com.github.mrag.rpc.util;

import com.github.mrag.rpc.annotation.ServiceProvider;

/**
 * 反射工具类，提供方便的静态反射方法
 *
 * @author Gmw
 */
public final class ReflectionUtil {
    /**
     * 判断传入的对象是否满足Provider条件。
     * <p>
     * Provider必须添加ServiceProvider注解，且具有一个接口。
     * 如果有多个接口，那么必须在注解中指定使用哪一个接口。
     *
     * @param bean 传入的bean
     * @return 如果满足Provider条件返回true，否则false
     */
    public static boolean isProvider(Object bean) {
        ServiceProvider anno = bean.getClass().getDeclaredAnnotation(ServiceProvider.class);
        return false;
    }
}
