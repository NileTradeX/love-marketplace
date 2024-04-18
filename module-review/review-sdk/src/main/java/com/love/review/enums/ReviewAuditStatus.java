package com.love.review.enums;

public enum ReviewAuditStatus {
    PENDING(0, "Pending"),
    APPROVED(1, "Approved"),
    REJECTED(2, "Rejected"),
    ;

    private final int status;
    private final String name;

    ReviewAuditStatus(int status, String name) {
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
