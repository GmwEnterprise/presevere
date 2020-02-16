package com.example.cloud.licenses.controllers;

import com.example.cloud.licenses.services.DiscoveryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "v1/tools")
public class ToolsController {
    @Resource
    private DiscoveryService discoveryService;

    @RequestMapping(value = "/eureka/services", method = RequestMethod.GET)
    public List<String> getEurekaServices() {
        return discoveryService.getEurekaServices();
    }
}
