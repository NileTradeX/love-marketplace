package com.love.merchant.backend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CategoryTreeVO implements Serializable {
    private Long id;
    private Long pid;
    private String name;
    private Integer level;
    private Integer sortNum;
    private Integer type;
    private String ids;
    private List<CategoryTreeVO> children;
}
