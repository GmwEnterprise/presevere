package org.exmaple.sample;

import org.apache.dubbo.config.annotation.Reference;
import org.example.sample.api.HelloService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author Gmw
 */
@SpringBootApplication
public class SpringbootConsumerApplication {
    @Reference(url = "dubbo://127.0.0.1:20880/org.example.sample.provider.HelloServiceImpl")
    private HelloService helloService;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootConsumerApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner() {
        return arg -> System.out.println(helloService.sayHello("Mic"));
    }
}
