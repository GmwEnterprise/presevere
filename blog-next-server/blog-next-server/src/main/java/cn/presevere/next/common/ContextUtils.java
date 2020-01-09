package cn.presevere.next.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

public final class ContextUtils {

    /**
     * 通过bean的类型获取bean
     */
    public static <T> T getBean(Class<T> type) {
        return ApplicationContextAwareImpl.applicationContext.getBean(type);
    }

    /**
     * 通过bean的名称获取bean
     */
    public static Object getBean(String beanName) {
        return ApplicationContextAwareImpl.applicationContext.getBean(beanName);
    }

    @Component
    private static class ApplicationContextAwareImpl implements ApplicationContextAware {
        private static ApplicationContext applicationContext;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            ApplicationContextAwareImpl.applicationContext = applicationContext;
        }
    }
}
