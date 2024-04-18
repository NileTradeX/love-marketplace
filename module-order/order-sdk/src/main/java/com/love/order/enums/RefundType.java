package com.love.order.enums;

public enum RefundType {
    PART(1, "Part"),
    FULL(2, "Full");

    private final int type;
    private final String desc;

    RefundType(int status, String desc) {
        this.type = status;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
