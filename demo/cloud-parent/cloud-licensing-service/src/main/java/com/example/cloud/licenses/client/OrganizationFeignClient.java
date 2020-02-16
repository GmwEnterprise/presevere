package com.example.cloud.licenses.client;

import com.example.cloud.common.model.Organization;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("organizationservice")
public interface OrganizationFeignClient {

    @RequestMapping(
        method = RequestMethod.GET,
        value = "/v1/organizations/{organizationId}",
        consumes = "application/json"
    )
    Organization getOrganization(@PathVariable(value = "organizationId") String organizationId);
}
