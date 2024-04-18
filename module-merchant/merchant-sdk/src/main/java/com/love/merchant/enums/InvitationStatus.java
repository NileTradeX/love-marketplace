package com.love.merchant.enums;

public enum InvitationStatus {

    WAIT_SEND(0, "await send"),
    WAIT_SUBMIT(1, "await submit"),
    WAIT_REVIEW(2, "await review"),
    APPROVE(5, "approve"),
    REJECT(10, "reject");

    private final int status;
    private final String desc;

    InvitationStatus(int status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
