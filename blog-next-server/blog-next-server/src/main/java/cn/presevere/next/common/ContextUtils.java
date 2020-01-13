package cn.presevere.next.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring工具类
 */
public final class ContextUtils {

    private static ApplicationContext context = null;

    /**
     * 通过bean的类型获取bean
     */
    public static <T> T getBean(Class<T> type) {
        return context.getBean(type);
    }

    /**
     * 通过bean的名称获取bean
     */
    public static Object getBean(String beanName) {
        return context.getBean(beanName);
    }

    @Component
    private static class ApplicationContextAwareImpl implements ApplicationContextAware {

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            ContextUtils.context = applicationContext;
        }
    }
}
