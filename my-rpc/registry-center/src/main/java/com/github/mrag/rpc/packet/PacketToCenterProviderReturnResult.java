package com.github.mrag.rpc.packet;

public class PacketToCenterProviderReturnResult implements Packet {
    @Override
    public PacketType type() {
        return PacketType.TO_CENTER_PROVIDER_RETURN_RESULT;
    }
}
