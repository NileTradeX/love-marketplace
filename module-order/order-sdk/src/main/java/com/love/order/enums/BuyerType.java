package com.love.order.enums;

public enum BuyerType {

    LOVE_ACCOUNT(0, "Love Account"),
    GUEST(1, "Guest");

    private final int type;
    private final String desc;

    private BuyerType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
