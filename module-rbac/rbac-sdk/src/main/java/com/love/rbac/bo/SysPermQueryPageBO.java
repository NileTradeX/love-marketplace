package com.love.rbac.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysPermQueryPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private String title;
    private Integer pid;
}
