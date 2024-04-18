package com.love.merchant.enums;

public enum MerUserType {

    ADMIN(1, "admin"),
    NORMAL(2, "normal");

    private final int type;
    private final String desc;

    MerUserType(int type, String desc) {
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
