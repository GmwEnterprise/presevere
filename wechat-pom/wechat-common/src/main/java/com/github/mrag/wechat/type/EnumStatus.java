package com.github.mrag.wechat.type;

public enum EnumStatus {
    VALID(1, "有效"),
    INVALID(0, "无效/失效");

    private final int status;
    private final String desc;

    EnumStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public int getStatus() {
        return status;
    }

    public static EnumStatus get(int value) {
        return value == 0 ? INVALID : VALID;
    }
}
