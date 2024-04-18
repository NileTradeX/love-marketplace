package com.love.goods.enums;

public enum LabelType {
    INGREDIENT(0, "Ingredient"),
    BENEFIT(1, "Benefit");

    private final int type;
    private final String name;

    LabelType(int type, String name) {
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
