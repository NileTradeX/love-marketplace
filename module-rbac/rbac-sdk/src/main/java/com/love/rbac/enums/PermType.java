package com.love.rbac.enums;

public enum PermType {

    DIR(1, "dir"),
    PAGE(2, "page"),
    BUTTON(3, "button");

    private final int type;
    private final String desc;

    PermType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }
}
