package org.example.sample.provider;

import org.example.sample.api.HelloService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {
    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        return String.format("[%s]: Hello, %s%n", serviceName, name);
    }
}
