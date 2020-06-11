package com.github.mrag.wechat.im.packets.codec;

public interface Codec {

    <T> byte[] encode(T packet) throws Exception;

    <T> T decode(byte[] in, Class<T> type) throws Exception;
}
