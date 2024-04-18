package com.love.order.enums;

public enum AfterSaleDealResult {
    TO_DEAL(0, "TO_DEAL"),

    APPROVE(1, "APPROVE"),

    REFUSE(2, "REFUSE");

    private Integer code;

    private String title;

    AfterSaleDealResult(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
