package com.love.influencer.enums;

public enum InfUserStatus {
    INACTIVE(-1), PENDING(0), ACTIVE(1);

    private final int status;

    InfUserStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
