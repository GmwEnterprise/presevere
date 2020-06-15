package com.github.mrag.rpc.repo;

import com.google.common.base.Objects;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 远程服务组
 */
public final class RemoteServiceGroup {
    private String serviceName; // 服务名
    private String serviceVersion; // 服务版本号
    private String group; // 服务组别

    private List<RemoteServiceUnit> units; // 所有服务单元实例

    /**
     * 添加服务单元
     *
     * @param ip   ip
     * @param port port
     */
    void addUnit(String ip, int port) {
        RemoteServiceUnit unit = new RemoteServiceUnit()
                .setFromIp(ip)
                .setFromPort(port);
        if (units == null) {
            units = new ArrayList<>();
            units.add(unit.setAvailability(true)
                    .setRegistryTime(LocalDateTime.now()));
        } else {
            int index = units.indexOf(unit);
            if (index > -1) {
                units.get(index).setAvailability(true).setRegistryTime(LocalDateTime.now());
            } else {
                units.add(unit.setAvailability(true).setRegistryTime(LocalDateTime.now()));
            }
        }
    }

    /**
     * 该服务组是否有可用的服务
     *
     * @return 是/否
     */
    private boolean available() {
        return units.stream().anyMatch(unit -> unit.availability);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RemoteServiceGroup that = (RemoteServiceGroup) o;
        return Objects.equal(serviceName, that.serviceName) &&
                Objects.equal(serviceVersion, that.serviceVersion) &&
                Objects.equal(group, that.group);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(serviceName, serviceVersion, group);
    }

    public String getServiceName() {
        return serviceName;
    }

    public RemoteServiceGroup setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public RemoteServiceGroup setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
        return this;
    }

    public String getGroup() {
        return group;
    }

    public RemoteServiceGroup setGroup(String group) {
        this.group = group;
        return this;
    }

    public List<RemoteServiceUnit> getUnits() {
        return units;
    }

    public RemoteServiceGroup setUnits(List<RemoteServiceUnit> units) {
        this.units = units;
        return this;
    }

    /**
     * 远程服务单元
     */
    private static class RemoteServiceUnit {
        private String fromIp; // 提供方IP地址
        private Integer fromPort; // 提供方端口号
        private LocalDateTime registryTime; // 单元服务注册时间
        private Boolean availability; // 单元服务是否可用

        public String getFromIp() {
            return fromIp;
        }

        public RemoteServiceUnit setFromIp(String fromIp) {
            this.fromIp = fromIp;
            return this;
        }

        public Integer getFromPort() {
            return fromPort;
        }

        public RemoteServiceUnit setFromPort(Integer fromPort) {
            this.fromPort = fromPort;
            return this;
        }

        public LocalDateTime getRegistryTime() {
            return registryTime;
        }

        public RemoteServiceUnit setRegistryTime(LocalDateTime registryTime) {
            this.registryTime = registryTime;
            return this;
        }

        public Boolean getAvailability() {
            return availability;
        }

        public RemoteServiceUnit setAvailability(Boolean availability) {
            this.availability = availability;
            return this;
        }
    }
}
