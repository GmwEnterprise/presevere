package com.github.mrag.rpc;

import java.time.LocalDateTime;

/**
 * 远程服务定义
 */
public final class RemoteService {
    private String serviceName; // 服务名
    private String serviceVersion; // 服务版本号
    private String group; // 服务组别

    // 以上三个元素用于定位一个服务

    private LocalDateTime registryTime; // 服务注册时间
    private Boolean availability; // 服务是否可用

    public RemoteService() {
    }

    public RemoteService(String serviceName, String serviceVersion, String group) {
        this.serviceName = serviceName;
        this.serviceVersion = serviceVersion;
        this.group = group;
        this.availability = true;
        this.registryTime = LocalDateTime.now();
    }

    public String getServiceName() {
        return serviceName;
    }

    public RemoteService setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public RemoteService setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public RemoteService setGroup(String group) {
        this.group = group;
        return this;
    }

    public LocalDateTime getRegistryTime() {
        return registryTime;
    }

    public RemoteService setRegistryTime(LocalDateTime registryTime) {
        this.registryTime = registryTime;
        return this;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public RemoteService setAvailability(Boolean availability) {
        this.availability = availability;
        return this;
    }
}
