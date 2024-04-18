package com.love.order.enums;

public enum OrderStatus {

    AWAIT_PAYMENT(0, "Await Payment"),
    PENDING_SHIPMENT(10, "Pending Shipment"),
    PENDING_RECEIPT(20, "Pending Receipt"),
    AFTER_SALES(30, "After-Sales"),

    COMPLETED(40, "Completed"),
    CLOSED(50, "Closed");

    private final int status;
    private final String desc;

    private OrderStatus(int status, String desc) {
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
