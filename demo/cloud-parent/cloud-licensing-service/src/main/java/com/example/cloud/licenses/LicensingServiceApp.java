package com.example.cloud.licenses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan("com.example.cloud.common.model")
public class LicensingServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(LicensingServiceApp.class, args);
    }

    @Bean(name = "restTemplate")
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
