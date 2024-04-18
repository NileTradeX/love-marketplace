package com.love.user.sdk.enums;

public enum UserStatus {
    DEACTIVATED(0, "deactivated"),
    ACTIVATED(1, "activated");

    private final int status;
    private final String name;

    UserStatus(int status, String name) {
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
