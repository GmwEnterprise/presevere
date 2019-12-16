package cn.gmwenterprise.presevere.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "cn.gmwenterprise.presevere.dao")
public class MybatisConfig {}
