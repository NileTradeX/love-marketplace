package com.love.merchant.enums;

public enum PermType {

    DIR(1, "dir"),
    PAGE(2, "page"),
    BUTTON(3, "button");

    private final int val;
    private final String desc;

    PermType(int val, String desc) {
        this.val = val;
        this.desc = desc;
    }

    public int getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }
}
