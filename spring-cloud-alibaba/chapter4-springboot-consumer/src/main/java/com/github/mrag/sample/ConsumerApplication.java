package com.github.mrag.sample;

import com.github.mrag.sample.api.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @Reference(url = "dubbo://192.168.3.18:20880/com.github.mrag.sample.api.HelloService")
    private HelloService helloService;

    @Bean
    public ApplicationRunner runner() {
        return args -> System.out.println(helloService.sayHello("Gmw"));
    }
}
