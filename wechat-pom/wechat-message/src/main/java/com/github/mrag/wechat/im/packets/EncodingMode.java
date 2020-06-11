package com.github.mrag.wechat.im.packets;

/**
 * 编解码方式
 */
public enum EncodingMode {
    /**
     * JSON编码
     */
    JSON(1),

    /**
     * 无需编码
     */
    NON_ENCODING(2);

    private final byte mode;

    EncodingMode(int mode) {
        this.mode = (byte) mode;
    }

    public byte getMode() {
        return mode;
    }

    public static EncodingMode mode(byte mode) {
        for (EncodingMode value : values()) {
            if (value.mode == mode) {
                return value;
            }
        }
        throw new IllegalArgumentException("没有该EncodingMode");
    }
}
