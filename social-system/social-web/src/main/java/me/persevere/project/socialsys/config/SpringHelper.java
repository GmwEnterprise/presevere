package me.persevere.project.socialsys.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
public class SpringHelper implements ApplicationContextAware {
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringHelper.applicationContext == null) {
            SpringHelper.applicationContext = applicationContext;
        }
    }

    @PostConstruct
    public void doSomething() {
        DataSource ds = applicationContext.getBean(DataSource.class);
//        SqlSessionFactoryBean ssf = applicationContext.getBean(SqlSessionFactoryBean.class);
        System.out.println("==> " + ds);
//        System.out.println("==> " + ssf);
    }
}
