package com.github.mrag.wechat.im.packets;

import com.github.mrag.wechat.im.EnumCommand;

import static com.github.mrag.wechat.im.EnumCommand.SINGLE_MSG_TO_MANY_USER;

public class SingleMsgToManyUserPacket implements Packet {
    @Override
    public EnumCommand command() {
        return SINGLE_MSG_TO_MANY_USER;
    }

    @Override
    public int commandValue() {
        return SINGLE_MSG_TO_MANY_USER.getCommand();
    }
}
