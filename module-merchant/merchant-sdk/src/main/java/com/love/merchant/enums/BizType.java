package com.love.merchant.enums;

public enum BizType {
    CORPORATION_PRIVATE(1, "Corporation (Private) "),
    CORPORATION_PUBLIC(2, "Corporation (Public) "),
    INTERNATIONAL_ORGANIZATION(3, "International Organization"),
    LIMITED_LIABILITY_COMPANY(4, "Limited Liability Company"),
    INDIVIDUAL_SOLE_PROPRIETORSHIP(5, "Individual Sole Proprietorship"),
    PARTNERSHIP(6, "Partnership");

    private final int type;
    private final String desc;

    BizType(int type, String desc) {
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
