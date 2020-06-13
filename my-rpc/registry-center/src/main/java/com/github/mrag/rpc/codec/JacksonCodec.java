package com.github.mrag.rpc.codec;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mrag.rpc.ProjectConfig;
import com.github.mrag.rpc.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.util.CharsetUtil;

import java.util.List;

/**
 * JSON序列化方式
 */
public class JacksonCodec extends ByteToMessageCodec<Packet> {
    private final ObjectMapper objectMapper = ProjectConfig.getObjectMapper();

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        byte[] bytes = objectMapper.writeValueAsString(msg).getBytes(CharsetUtil.UTF_8);
        out.writeInt(bytes.length); // 先写一个32位的长度值摆在第一位
        out.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int len = in.readInt();
        byte[] bytesIn = new byte[len];
        in.readBytes(bytesIn);
        String json = new String(bytesIn, CharsetUtil.UTF_8);
        Packet packet = objectMapper.readValue(json, Packet.class);
        out.add(packet);
    }
}
