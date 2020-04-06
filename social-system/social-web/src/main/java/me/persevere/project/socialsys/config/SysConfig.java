package me.persevere.project.socialsys.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SysConfig {

    @Configuration
    @MapperScan(basePackages = "me.persevere.project.socialsys.dao")
    public static class Dao {
    }

    @Configuration
    @EnableWebMvc
    public static class Web implements WebMvcConfigurer {
    }
}
