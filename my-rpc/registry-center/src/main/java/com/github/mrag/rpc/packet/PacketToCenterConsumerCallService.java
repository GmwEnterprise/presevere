package com.github.mrag.rpc.packet;

public class PacketToCenterConsumerCallService implements Packet {
    @Override
    public PacketType type() {
        return PacketType.TO_CENTER_CONSUMER_CALL_SERVICE;
    }
}
