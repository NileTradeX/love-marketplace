package com.love.order.enums;

public enum AfterSaleStatus {

    //1.Refund requested 2.Refunded 3.Cancelled 4.Rejected
    REQUESTED(1, "Refund requested"),
    REFUNDING(2, "Refunding"),
    REFUNDED(3, "Refunded"),
    CANCELLED(4, "Cancelled"),
    DECLINED(5, "Declined"),
    REFUND_FAIL(6, "Refund Fail"),;

    private final int status;
    private final String desc;

    AfterSaleStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
