package com.love.rbac.enums;

public enum SysUserType {

    SUPER(1, "super"),
    NORMAL(2, "normal");

    private final int type;
    private final String desc;

    SysUserType(int type, String desc) {
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
