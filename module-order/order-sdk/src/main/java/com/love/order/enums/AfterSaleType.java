package com.love.order.enums;

public enum AfterSaleType {
    REFUND_ONLY(1, "Refund Only"),;

    private final int type;
    private final String desc;

    AfterSaleType(int status, String desc) {
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
