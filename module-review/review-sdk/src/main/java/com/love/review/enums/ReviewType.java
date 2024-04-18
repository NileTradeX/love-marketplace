package com.love.review.enums;

public enum ReviewType {
    GOODS(1, "Goods"),
    ;

    private final int type;
    private final String name;

    ReviewType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
