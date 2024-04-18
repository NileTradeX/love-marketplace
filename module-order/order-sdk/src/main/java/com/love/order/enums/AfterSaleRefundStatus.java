package com.love.order.enums;

public enum AfterSaleRefundStatus {
    ////0not refund 1refunded 2close
    REFUND_DEFAULT(0, "default"),
    REFUND_SUCCESS(1, "refund success"),
    REFUND_CLOSE(2, "refund close"),
    REFUND_FAIL(3, "refund fail"),;

    private final int type;
    private final String desc;

    AfterSaleRefundStatus(int status, String desc) {
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
