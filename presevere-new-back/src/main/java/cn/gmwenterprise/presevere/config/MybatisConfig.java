package cn.gmwenterprise.presevere.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;

@Configuration
@MapperScan(
    basePackages = "cn.gmwenterprise.presevere.dao",
    annotationClass = Repository.class)
public class MybatisConfig {
}
