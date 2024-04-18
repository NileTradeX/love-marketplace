package com.love.goods.enums;

import java.util.Arrays;
import java.util.function.Supplier;

public enum CategoryType {
    PUBLISHING(0, "Publishing Category"),
    DISPLAY(1, "Display Category");

    private final int type;
    private final String name;

    CategoryType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static CategoryType from(int status, Supplier<RuntimeException> supplier) {
        return Arrays.stream(CategoryType.values()).filter(x -> status == x.getType()).findAny().orElseThrow(supplier);
    }
}
