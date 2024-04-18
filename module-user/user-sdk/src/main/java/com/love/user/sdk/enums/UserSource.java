package com.love.user.sdk.enums;

public enum UserSource {

    LOVE(0, "love"),
    BOLT(1, "bolt"),
    CHECKOUT(2, "checkout"),

    ;

    private final int source;
    private final String name;

    UserSource(int source, String name) {
        this.source = source;
        this.name = name;
    }

    public int getSource() {
        return source;
    }

    public String getName() {
        return name;
    }
}
