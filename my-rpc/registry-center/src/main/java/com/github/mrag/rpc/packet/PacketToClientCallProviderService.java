package com.github.mrag.rpc.packet;

public class PacketToClientCallProviderService implements Packet {
    @Override
    public PacketType type() {
        return PacketType.TO_CLIENT_CALL_PROVIDER_SERVICE;
    }
}
