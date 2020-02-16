package com.example.cloud.licenses.client;

import com.example.cloud.common.model.Organization;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;

/**
 * 使用DiscoveryClient调用组织服务
 */
@Component
public class OrganizationDiscoveryClient {
    // 自动注入系统提供的bean
    @Resource
    private DiscoveryClient discoveryClient;

    public Organization getOrganization(String orgId) {
        RestTemplate restTemplate = new RestTemplate();

        // 获取组织服务的所有实例的列表
        List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");
        if (instances.size() == 0) {
            return null; // 一个服务都没有则返回空
        }
        String serviceUrl = String.format("%s/v1/organizations/%s",
            // 未使用负载均衡
            instances.get(0).getUri().toString(),
            orgId);

        ResponseEntity<Organization> restExchange = restTemplate
            .exchange(serviceUrl, HttpMethod.GET, null, Organization.class, orgId);
        return restExchange.getBody();
    }
}
