package com.example.multi.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class App1 {
    public static void main(String[] args) {
        SpringApplication.run(App1.class, args);
    }

    @GetMapping("/one")
    public String one() {
        return "One!";
    }
}
