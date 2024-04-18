package com.love.goods.enums;

public enum SkuStatus {
    DISABLE(0, "disable"),
    ENABLE(1, "enable");

    private final int status;
    private final String name;

    SkuStatus(int status, String name) {
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
