package com.github.mrag.rpc.repo;

public class ServiceMessage {
    private String serviceName; // 服务名
    private String serviceVersion; // 服务版本号
    private String group; // 服务组别

    private String fromIp; // 提供方IP地址
    private Integer fromPort; // 提供方端口号
    private String registryTime; // 单元服务注册时间
    private Boolean availability; // 单元服务是否可用

    public String getServiceName() {
        return serviceName;
    }

    public ServiceMessage setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public ServiceMessage setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public ServiceMessage setGroup(String group) {
        this.group = group;
        return this;
    }

    public String getFromIp() {
        return fromIp;
    }

    public ServiceMessage setFromIp(String fromIp) {
        this.fromIp = fromIp;
        return this;
    }

    public Integer getFromPort() {
        return fromPort;
    }

    public ServiceMessage setFromPort(Integer fromPort) {
        this.fromPort = fromPort;
        return this;
    }

    public String getRegistryTime() {
        return registryTime;
    }

    public ServiceMessage setRegistryTime(String registryTime) {
        this.registryTime = registryTime;
        return this;
    }

    public Boolean getAvailability() {
        return availability;
    }

    public ServiceMessage setAvailability(Boolean availability) {
        this.availability = availability;
        return this;
    }
}
