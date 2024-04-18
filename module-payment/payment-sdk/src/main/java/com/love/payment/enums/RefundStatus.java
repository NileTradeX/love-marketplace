package com.love.payment.enums;

public enum RefundStatus {

    PROCESSING(0, "Processing"),
    SUCCEEDED(1, "Succeeded"),
    FAILED(2, "Failed");

    private final int status;
    private final String name;

    RefundStatus(int status, String name) {
        this.status = status;
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }
}
