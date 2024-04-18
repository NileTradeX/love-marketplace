package com.love.rbac.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysPermDTO implements Serializable {
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
    private List<SysPermDTO> children;
}
