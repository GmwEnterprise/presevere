package cn.gmwenterprise.website.constants;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Sex {
    KEEP_SECRET(1, "保密"),
    MALE(2, "男"),
    FEMALE(3, "女");

    int value;
    String name;

    Sex(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public static Sex of(int value) {
        return Arrays.stream(values())
            .filter(item -> item.value == value)
            .findFirst()
            .orElse(KEEP_SECRET);
    }
}
