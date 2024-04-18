package com.love.order.enums;

public enum AfterSalesType {
    REFUND_ONLY(1, "Refund Only"),
    RETURN_AND_REFUND(2, "Return and Refund");

    private final int type;
    private final String desc;

    AfterSalesType(int status, String desc) {
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
