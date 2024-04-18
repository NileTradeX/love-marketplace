package com.love.payment.enums;

public enum SplitType {

    BALANCEACCOUNT("BalanceAccount"),
    COMMISSION("Commission");

    private final String value;

    SplitType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
