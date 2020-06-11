package com.github.mrag.wechat.im.packets;

import java.io.Serializable;

/**
 * 数据包
 */
public class Packet<T> {
    private final PacketHeader header;
    private final T body;

    public Packet(PacketHeader header, T body) {
        this.header = header;
        this.body = body;
    }

    public PacketHeader getHeader() {
        return header;
    }

    public T getBody() {
        return body;
    }

    public static class PacketHeader {
        public static final int HEADER_LEN = 14; // 8+1+1+4
        // 8位, 发送时间
        private long sendTimeMill;
        // 1位, 数据包类型
        private PacketType packetType;
        // 1位, 序列化方式
        private EncodingMode encodingMode;
        // 4位, 包体长度, 该值要放在头部的最后，便于长度域划分
        private int bodyLength;

        public long getSendTimeMill() {
            return sendTimeMill;
        }

        public PacketType getPacketType() {
            return packetType;
        }

        public void setPacketType(PacketType packetType) {
            this.packetType = packetType;
        }

        public EncodingMode getEncodingMode() {
            return encodingMode;
        }

        public void setEncodingMode(EncodingMode encodingMode) {
            this.encodingMode = encodingMode;
        }

        public int getBodyLength() {
            return bodyLength;
        }

        public void setBodyLength(int bodyLength) {
            this.bodyLength = bodyLength;
        }

        public void setSendTimeMill(long sendTimeMill) {
            this.sendTimeMill = sendTimeMill;
        }
    }
}
