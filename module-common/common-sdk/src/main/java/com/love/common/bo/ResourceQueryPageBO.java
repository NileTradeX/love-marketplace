package com.love.common.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResourceQueryPageBO implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    private String name;
    private Integer type;
}
