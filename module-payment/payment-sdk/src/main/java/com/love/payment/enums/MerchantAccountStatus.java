package com.love.payment.enums;

public enum MerchantAccountStatus {

    NEED_ONBOARDING(0, "Need Onboarding"),
    ONBOARDING_IN_PROCESS(1, "Onboarding in process"),
    COMPLETED(2, "Completed");

    private final int status;
    private final String name;

    MerchantAccountStatus(int status, String name) {
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
