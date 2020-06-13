package com.github.mrag.rpc.packet;

public class PacketToClientHeartbeat implements Packet {
    @Override
    public PacketType type() {
        return PacketType.TO_CLIENT_HEARTBEAT;
    }
}
