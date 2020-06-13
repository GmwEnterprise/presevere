package com.github.mrag.rpc.packet;

/**
 * 客户端注册服务
 */
public class PacketToCenterRegistryServices implements Packet {
    @Override
    public PacketType type() {
        return PacketType.TO_CENTER_REGISTRY_SERVICES;
    }
}
