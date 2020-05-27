package com.github.mrag.wechat.type;

public enum EnumRelationType {
    FRIEND(1, "朋友"),
    BLACKLIST(2, "黑名单");

    private final int value;
    private final String desc;

    EnumRelationType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static EnumRelationType get(int value) {
        for (EnumRelationType item : values()) {
            if (item.value == value) {
                return item;
            }
        }
        return null;
    }
}
