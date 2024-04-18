package com.love.influencer.enums;

public enum GoodsSortType {
    NEWEST(0, "Newest"),
    SALES_VOLUME(1, "Sales Volume"),
    PRICE_LOW_TO_HIGH(2, "Price Low To High"),
    PRICE_HIGH_TO_LOW(3, "Price High To Low"),
    COMMUNITY_SCORE(4, "Community Score");

    private final int sortType;
    private final String name;

    GoodsSortType(int sortType, String name) {
        this.sortType = sortType;
        this.name = name;
    }

    public int getSortType() {
        return sortType;
    }

    public String getName() {
        return name;
    }
}
