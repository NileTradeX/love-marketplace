package com.love.rbac.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysPermSaveBO implements Serializable {
    private String title;
    private Integer pid;
    private Integer type;
    private String code;
    private String icon;
    private String path;
    private Integer sortNum;
    private String apis;
}
