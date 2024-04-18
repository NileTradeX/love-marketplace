package com.love.goods.enums;

import java.util.Arrays;
import java.util.function.Supplier;

public enum GoodsType {
    PHYSICAL(1, "Physical Product"),
    DIGITAL(2, "Digital Product or Service");

    private final int type;
    private final String name;

    GoodsType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static GoodsType from(int status, Supplier<RuntimeException> supplier) {
        return Arrays.stream(GoodsType.values()).filter(x -> status == x.getType()).findAny().orElseThrow(supplier);
    }
}
