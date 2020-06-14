package com.github.mrag.rpc.controller;

import com.github.mrag.rpc.repo.RemoteServiceGroup;
import com.github.mrag.rpc.repo.RpcRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/dashboard")
public class RpcDashboardController {
    @Resource
    RpcRepository repository;

    @GetMapping("/providerList")
    public List<RemoteServiceGroup> providerList() {
        return repository.getServiceList();
    }
}
