package com.love.order.enums;

import java.util.Arrays;

public enum RefundStatus {
    REFUNDING(0, "refunding"),
    REFUND_SUCCESS(1, "refund success"),
    REFUND_FAIL(2, "refund fail"),
    ;

    private final int type;
    private final String desc;

    RefundStatus(int status, String desc) {
        this.type = status;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public static RefundStatus getByType(int type) {
        return Arrays.stream(RefundStatus.values())
                .filter(refundStatus -> refundStatus.getType() == type)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid type: " + type));
    }
}
