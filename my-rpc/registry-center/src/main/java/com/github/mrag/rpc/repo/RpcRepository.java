package com.github.mrag.rpc.repo;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RpcRepository {
    /**
     * 所有服务存储于此
     */
    private static final List<RemoteServiceGroup> SERVICE_GROUPS = new ArrayList<>();

    // TODO 线程同步待优化
    public synchronized void registryService(ServiceMessage serviceMessage) {
        RemoteServiceGroup group = new RemoteServiceGroup()
                .setServiceName(serviceMessage.getServiceName())
                .setServiceVersion(serviceMessage.getServiceVersion())
                .setGroup(serviceMessage.getGroup());
        int index;
        if ((index = SERVICE_GROUPS.indexOf(group)) > -1) {
            group = SERVICE_GROUPS.get(index);
        }

        group.addUnit(serviceMessage.getFromIp(), serviceMessage.getFromPort());
    }

    public List<RemoteServiceGroup> getServiceList() {
        return new ArrayList<>(SERVICE_GROUPS);
    }
}
