package com.love.merchant.enums;

public enum MerShippingModel {

    FIXED(0, "Fixed fee for order"),
    TIERED(1, "Tiered fee for order"),
    FREE(2, "Free shipping on all orders");

    private final int model;
    private final String desc;

    MerShippingModel(int model, String desc) {
        this.model = model;
        this.desc = desc;
    }

    public int getModel() {
        return model;
    }

    public String getDesc() {
        return desc;
    }
}
