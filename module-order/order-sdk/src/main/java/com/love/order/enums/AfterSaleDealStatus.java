package com.love.order.enums;

public enum AfterSaleDealStatus {

    PREPARE_PROCESS(0, "PREPARE_PROCESS"),

    PROCESSED(1, "PROCESSED");


    private Integer code;

    private String title;

    AfterSaleDealStatus(Integer code, String title) {
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
