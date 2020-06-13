package com.github.mrag.rpc.packet;

public class PacketToClientSendResultToConsumer implements Packet {
    @Override
    public PacketType type() {
        return PacketType.TO_CLIENT_SEND_RESULT_TO_CONSUMER;
    }
}
