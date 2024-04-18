package com.love.goods.enums;

public enum GoodsStatus {
    DRAFT(0, "Draft"),
    UNDER_REVIEW(1, "Under Review"),
    REVIEW_REJECTED(2, "Review rejected"),
    ON_SALES(3, "On sales"),
    DELISTED(4, "Delisted"),
    ;

    private final int status;
    private final String name;

    GoodsStatus(int status, String name) {
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
