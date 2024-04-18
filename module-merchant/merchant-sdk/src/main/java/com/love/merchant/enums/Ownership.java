package com.love.merchant.enums;

public enum Ownership {

    PRIVATE(1, "Private"),
    PUBLIC(2, "Public");

    private final int val;
    private final String desc;

    Ownership(int val, String desc) {
        this.val = val;
        this.desc = desc;
    }

    public int getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }
}
