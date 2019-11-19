package cn.gmwenterprise.presevere.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SystemConfig {
    @PostConstruct
    public void init() {
        // 初始化数据工作
    }
}
