package com.github.mrag.rpc;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "rpc")
public class RpcProperties {
    public enum Serialization {
        JSON, PROTOBUF
    }

    private int port;
    private Serialization defaultSerialization;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Serialization getDefaultSerialization() {
        return defaultSerialization;
    }

    public void setDefaultSerial(String defaultSerial) {
        this.defaultSerialization = Serialization.valueOf(defaultSerial.toUpperCase());
    }
}