package com.github.mrag.rpc.packet;

/**
 * 消费方请求调用服务
 */
public class PacketToCenterConsumerCallService implements Packet {
    @Override
    public PacketType type() {
        return PacketType.TO_CENTER_CONSUMER_CALL_SERVICE;
    }
}
