package com.love.goods.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsDocDTO implements Serializable {
    private String coa;
    private Integer coaStatus;
    private String msds;
    private Integer msdsStatus;
    private String ppp;
    private Integer pppStatus;
    private String logfm;
    private Integer logfmStatus;
    private String rein;
    private Integer reinStatus;
}

