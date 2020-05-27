package com.github.mrag.wechat.im.packets;

import com.github.mrag.wechat.im.EnumCommand;

public class RequestPacket implements Packet {
    @Override
    public EnumCommand command() {
        return null;
    }

    @Override
    public int commandValue() {
        return 0;
    }
}
