package com.github.mrag.rpc;

import com.github.mrag.rpc.annotation.RpcConsumer;
import com.github.mrag.rpc.annotation.RpcProvider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class RpcServiceProcessor implements BeanPostProcessor {

    private void checkNotNull(Object... objects) {
        for (Object o : objects) {
            Objects.requireNonNull(o);
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        checkNotNull(bean, beanName);

        Class<?> beanType = bean.getClass();

        // 处理provider提供
        if (beanType.isAnnotationPresent(RpcProvider.class)) {
            // ProviderContainer将装配该bean
            System.out.println("发现provider -> " + beanType.getName() + "[beanName = " + beanName + "]");

            RpcProvider providerAnnotation = beanType.getAnnotation(RpcProvider.class);
            ProviderUnit providerUnit = new ProviderUnit()
                    .setName(providerAnnotation.name())
                    .setGroup(providerAnnotation.group())
                    .setVersion(providerAnnotation.version())
                    .setTarget(bean)
                    .setPublicApi(getPublicMethod(bean));

            ProviderContainer.addProvider(providerUnit);
        }

        // 处理consumer消费
        for (Field member : beanType.getDeclaredFields()) {
            if (member.isAnnotationPresent(RpcConsumer.class)) {
                // 为bean的这个member注入consumer代理
                System.out.println("consumer代理 -> " + member.getName());

                member.setAccessible(true);
                // TODO memberProxy为null，如何解决
                Object memberProxy = new ConsumerRemoteProxy<>(member.getType()).getProxy();
                try {
                    member.set(bean, memberProxy);
                } catch (IllegalAccessException e) {
                    throw new UnknownError(e.getMessage());
                }
            }
        }
        return bean;
    }

    private Map<String, Method> getPublicMethod(Object bean) {
        Class<?> beanType = bean.getClass();
        List<Method> methods = Arrays.stream(beanType.getMethods())
                // 所有的public方法
                .filter(method -> Modifier.isPublic(method.getModifiers())).collect(Collectors.toList());

        Map<String, Method> methodMap = new HashMap<>(methods.size());
        methods.forEach(method -> methodMap.put(method.getName(), method));
        return methodMap;
    }
}
