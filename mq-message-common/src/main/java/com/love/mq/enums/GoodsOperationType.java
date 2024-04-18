package com.love.mq.enums;


public enum GoodsOperationType {

    UPDATE(0, "UPDATE"),
    DELETE(1, "DELETE"),
    ;

    private final int type;
    private final String name;

    GoodsOperationType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
