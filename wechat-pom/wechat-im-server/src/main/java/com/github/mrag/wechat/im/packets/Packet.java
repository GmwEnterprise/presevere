package com.github.mrag.wechat.im.packets;

import com.github.mrag.wechat.im.EnumCommand;
import com.github.mrag.wechat.im.serialization.EnumSerializationMethod;

import java.io.Serializable;

public interface Packet extends Serializable {

    long serialVersionUID();

    EnumCommand command();

    /**
     * 默认使用JSON进行序列化，子类可覆盖
     *
     * @return 序列化方式，默认JSON
     */
    default EnumSerializationMethod serializationMethod() {
        return EnumSerializationMethod.JSON;
    }
}
