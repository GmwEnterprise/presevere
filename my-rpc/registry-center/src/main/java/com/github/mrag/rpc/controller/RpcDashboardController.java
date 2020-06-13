package com.github.mrag.rpc.controller;

import com.github.mrag.rpc.RemoteServiceGroup;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/dashboard")
public class RpcDashboardController {



    @GetMapping("/providerList")
    public List<RemoteServiceGroup> providerList() {

    }
}
