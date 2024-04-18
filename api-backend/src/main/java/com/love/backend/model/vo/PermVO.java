package com.love.backend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PermVO implements Serializable {
    private Long id;
    private String title;
    private Integer type;
    private Long pid;
    private String code;
    private String icon;
    private String path;
    private Integer sortNum;
    private String apis;
    private boolean checked;
    private List<PermVO> children;
}
