package com.love.order.enums;

public enum AfterSaleState {

//    ErrorEnum(0, "状态错误"),//
    TO_MERCHANT_DEAL_REFUND(1 ,"customer just applied"),
    TO_MERCHANT_DEAL_REFUND_RESULT(2 ,"merchant agreed"),
    REFUND_FAIL(98, "refund fail"),
    REFUND_SUCCESS(99, "complete refund"),
    CANCEL(100, "cancel"),;

    private final int code;
    private final String desc;

    AfterSaleState(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static AfterSaleState getEnum(Integer code) {
        for (AfterSaleState enumTemp : AfterSaleState.values()) {
            if (code.equals(enumTemp.getCode())) {
                return enumTemp;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
