package com.github.mrag.rpc.packet;

/**
 * 客户端注册服务
 */
public class PacketToCenterRegistryServices implements Packet {
    @Override
    public PacketType type() {
        return PacketType.TO_CENTER_REGISTRY_SERVICES;
    }

    // 定位服务
    private String serviceName;
    private String serviceGroup;
    private String serviceVersion;

    // api信息
    private String methodName; // 方法名称
    private Integer methodParamsTotal; // 方法一共有几个参数
    private Boolean hasReturnVal; // 方法是否有返回值

    // TODO 未完成
}
