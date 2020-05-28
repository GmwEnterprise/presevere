package com.github.mrag.wechat.im.packets;

import com.github.mrag.wechat.im.EnumCommand;
import com.github.mrag.wechat.im.serialization.EnumSerializationMethod;

import static com.github.mrag.wechat.im.EnumCommand.SINGLE_MSG_TO_ONE_USER;

public class SingleMsgToOneUserPacket implements Packet {
    @Override
    public EnumCommand command() {
        return SINGLE_MSG_TO_ONE_USER;
    }
}
