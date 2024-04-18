package com.love.order.enums;

public enum AfterSaleConsumeDealResult {

    NOT_DEAL(0, "NOT_DEAL"),
    CANCEL(1, "CANCEL"),

    ;

    private Integer code;

    private String title;

    AfterSaleConsumeDealResult(Integer code, String title) {
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
