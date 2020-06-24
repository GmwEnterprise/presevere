package com.github.mrag.rpc;

import com.github.mrag.rpc.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class LifeCycle implements BeanPostProcessor {
    private static final Logger log = LoggerFactory.getLogger(LifeCycle.class);

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (ReflectionUtil.isProvider(bean)) {
            // todo 保存提供者信息，整理该提供者所有的 public 调用方法
        }
        return bean;
    }
}
