package com.github.mrag.sample.web;

import com.github.mrag.sample.api.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Reference
    private HelloService helloService;

    @GetMapping("/say")
    public String sayHello() {
        return helloService.sayHello("Gmw");
    }
}
