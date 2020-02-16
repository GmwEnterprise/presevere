package com.example.cloud.licenses.services;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiscoveryService {
    @Resource
    RestTemplate restTemplate;
    @Resource
    DiscoveryClient discoveryClient;

    public List<String> getEurekaServices() {
        List<String> services = new ArrayList<>();
        discoveryClient.getServices().forEach(serviceName -> {
            discoveryClient.getInstances(serviceName).forEach(instance -> {
                services.add(String.format("%s:%s", serviceName, instance.getUri()));
            });
        });
        return services;
    }
}
