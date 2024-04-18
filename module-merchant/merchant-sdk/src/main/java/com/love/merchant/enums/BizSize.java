package com.love.merchant.enums;

public enum BizSize {
    TINY(1, "tiny business(under 25 employees)"),
    SMALL(2, "small business(under 150 employees)"),
    MID(3, "mid size business(under 500 employees)"),
    MID_LARGE(4, "mid-large business(under 1000 employees)"),
    LARGE(5, "large business(over 1000 employees)"),
    ;

    private final int type;
    private final String desc;

    BizSize(int type, String desc) {
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
