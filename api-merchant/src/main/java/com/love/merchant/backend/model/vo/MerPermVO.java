package com.love.merchant.backend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MerPermVO implements Serializable {
    private Long id;
    private String title;
    private Long pid;
    private Integer type;
    private String code;
    private String icon;
    private String path;
    private Integer sortNum;
    private String apis;
    private boolean checked;
    private List<MerPermVO> children;
}
