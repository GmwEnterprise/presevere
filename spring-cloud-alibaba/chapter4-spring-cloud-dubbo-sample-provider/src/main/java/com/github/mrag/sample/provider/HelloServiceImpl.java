package com.github.mrag.sample.provider;

import com.github.mrag.sample.api.HelloService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class HelloServiceImpl implements HelloService {

    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        return String.format("[%s]: Hello, %s", serviceName, name);
    }
}
