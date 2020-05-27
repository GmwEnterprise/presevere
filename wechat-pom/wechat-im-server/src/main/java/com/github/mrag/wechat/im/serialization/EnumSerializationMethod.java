package com.github.mrag.wechat.im.serialization;

public enum EnumSerializationMethod {
    JSON(1),
    HESSIAN(2);

    private final int method;

    EnumSerializationMethod(int method) {
        this.method = method;
    }

    public int getMethod() {
        return method;
    }

    public static EnumSerializationMethod of(int method) {
        for (EnumSerializationMethod item : values()) {
            if (item.method == method) {
                return item;
            }
        }
        return null;
    }
}
