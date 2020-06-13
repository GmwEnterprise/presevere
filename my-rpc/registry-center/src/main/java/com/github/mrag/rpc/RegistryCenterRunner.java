package com.github.mrag.rpc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        NettyRunner.class
})
public class RegistryCenterRunner {
    public static void main(String[] args) {
        SpringApplication.run(RegistryCenterRunner.class, args);
    }
}
