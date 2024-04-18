package com.love.common.enums;

public enum YesOrNo {

    YES(1, ""), NO(0, "");

    private final int val;
    private final String desc;

    YesOrNo(int val, String desc) {
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
