package com.love.merchant.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MerPermDTO implements Serializable {
    private Long id;
    private String title;
    private Integer pid;
    private Integer type;
    private String code;
    private String icon;
    private String path;
    private Integer sortNum;
    private String apis;
    private boolean checked;
    private List<MerPermDTO> children;
}
