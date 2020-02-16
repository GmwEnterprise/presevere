package com.example.cloud.organization;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan("com.example.cloud.common.model")
public class OrganizationServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(OrganizationServiceApp.class, args);
    }
}
