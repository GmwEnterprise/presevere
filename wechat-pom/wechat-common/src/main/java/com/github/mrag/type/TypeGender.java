package com.github.mrag.type;

public enum TypeGender {
    MALE(1, "男"),
    FEMALE(2, "女");

    private final int code;
    private final String desc;

    TypeGender(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static TypeGender get(int code) {
        for (TypeGender gender : values()) {
            if (gender.code == code) {
                return gender;
            }
        }
        return null;
    }
}
