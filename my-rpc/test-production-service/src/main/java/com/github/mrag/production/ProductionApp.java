package com.github.mrag.production;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.github.mrag.rpc", "com.github.mrag.production"})
public class ProductionApp {
    public static void main(String[] args) {
        SpringApplication.run(ProductionApp.class, args);
    }
}
