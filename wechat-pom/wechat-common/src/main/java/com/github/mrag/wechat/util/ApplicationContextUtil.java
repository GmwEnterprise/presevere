package com.github.mrag.wechat.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    private static ApplicationContext ctx = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ctx == null) {
            ctx = applicationContext;
        }
    }

    public static <Type> Type getBean(Class<Type> type) {
        return ctx.getBean(type);
    }

    public static Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }
}
