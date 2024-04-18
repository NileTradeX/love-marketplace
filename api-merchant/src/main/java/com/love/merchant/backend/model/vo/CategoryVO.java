package com.love.merchant.backend.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryVO implements Serializable {
    private Long id;
    private Long pid;
    private String name;
    private Integer level;
    private Integer sortNum;
    private Integer type;
    private String ids;
}
