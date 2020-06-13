package com.github.mrag.rpc;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RpcRepository {
    /**
     * 所有服务存储于此
     */
    private static final Set<RemoteServiceGroup> SERVICE_GROUPS = new HashSet<>();

    public void registryService() {
        // TODO 线程安全写set
    }
}
