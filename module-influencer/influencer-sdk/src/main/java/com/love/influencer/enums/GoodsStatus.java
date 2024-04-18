package com.love.influencer.enums;

public enum GoodsStatus {
    TO_BE_RECOMMENDED(0, "To Be Recommended"),
    RECOMMENDED(1, "Recommended"),
    INVALID(2, "Invalid"),
    ;

    private final int status;
    private final String name;

    GoodsStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
