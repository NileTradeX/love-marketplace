package com.love.goods.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategoryDTO implements Serializable {
    private Long id;
    private Long pid;
    private String name;
    private String alias;
    private String icon;
    private Integer level;
    private Integer sortNum;
    private Integer type;
    private String ids;
}
