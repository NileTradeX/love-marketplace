package com.love.payment.enums;

public enum PaymentStatus {

    PROCESSING(0, "Processing"),
    SUCCEEDED(1, "Succeeded"),
    FAILED(2, "Failed"),
    CANCELED(3, "Canceled");

    private final int status;
    private final String name;

    PaymentStatus(int status, String name) {
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
