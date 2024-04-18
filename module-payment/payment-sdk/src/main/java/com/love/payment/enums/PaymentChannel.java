package com.love.payment.enums;

public enum PaymentChannel {

    STRIPE(0, "Stripe"),
    ADYEN(1, "Adyen");

    private final int status;
    private final String name;

    PaymentChannel(int status, String name) {
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
