package com.github.mrag.wechat.im.packets;

import com.github.mrag.wechat.im.EnumCommand;

public interface Packet {

    EnumCommand command();

    int commandValue();
}
