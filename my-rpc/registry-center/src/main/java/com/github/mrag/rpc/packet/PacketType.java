package com.github.mrag.rpc.packet;

public enum PacketType {
    TO_CLIENT_HEARTBEAT(1), // 心跳包类型

    // 接收的数据包类型

    TO_CENTER_REGISTRY_SERVICES(6), // 每个客户端注册自己所提供的服务
    TO_CENTER_CONSUMER_CALL_SERVICE(2), // consumer调用服务
    TO_CENTER_PROVIDER_RETURN_RESULT(5), // provider返回执行结果

    // 发送的数据包类型

    TO_CLIENT_CALL_PROVIDER_SERVICE(3), // center调用provider
    TO_CLIENT_SEND_RESULT_TO_CONSUMER(4), // 将provider返回的执行结果返送给consumer

    ;

    private final int typeVal;

    PacketType(int typeVal) {
        this.typeVal = typeVal;
    }

    public int getTypeVal() {
        return typeVal;
    }

    public static PacketType of(int typeVal) {
        for (PacketType packetType : values()) {
            if (packetType.typeVal == typeVal) {
                return packetType;
            }
        }
        throw new RuntimeException("错误的数据包");
    }
}
