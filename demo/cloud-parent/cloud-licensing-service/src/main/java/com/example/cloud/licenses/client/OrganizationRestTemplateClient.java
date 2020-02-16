package com.example.cloud.licenses.client;

import com.example.cloud.common.model.Organization;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@Component
public class OrganizationRestTemplateClient {
    @Resource
    RestTemplate restTemplate;

    public Organization getOrganization(String orgId) {
        ResponseEntity<Organization> restExchange = restTemplate.exchange(

            // Ribbon会自动解析这个url，将organizationservice替换为应用对应的真实地址
            // 将会使用到负载均衡，Ribbon会轮询robin
            "http://organizationservice/v1/organizations/{orgId}",

            HttpMethod.GET,
            null, Organization.class, orgId);
        return restExchange.getBody();
    }
}
