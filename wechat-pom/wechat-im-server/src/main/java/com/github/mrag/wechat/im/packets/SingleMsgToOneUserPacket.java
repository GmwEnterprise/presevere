package com.github.mrag.wechat.im.packets;

import com.github.mrag.wechat.im.EnumCommand;

import static com.github.mrag.wechat.im.EnumCommand.SINGLE_MSG_TO_ONE_USER;

public class SingleMsgToOneUserPacket implements Packet {
    private static final long serialVersionUID = 1L;

    @Override
    public long serialVersionUID() {
        return SingleMsgToOneUserPacket.serialVersionUID;
    }

    @Override
    public EnumCommand command() {
        return SINGLE_MSG_TO_ONE_USER;
    }
}
