package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class CategorySaveBO implements Serializable {
    private String name;
    private String alias;
    private String icon;
    private Long pid = 0L;
    private Integer level = 1;
    private Integer sortNum = 1;
    private Integer type;
    private String ids;
    private Long oldPid;
}
