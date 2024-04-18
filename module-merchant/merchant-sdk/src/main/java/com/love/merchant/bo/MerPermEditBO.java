package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerPermEditBO implements Serializable {
    private Long id;
    private String title;
    private Integer type;
    private String code;
    private String icon;
    private String path;
    private Integer sortNum;
    private String apis;
}
