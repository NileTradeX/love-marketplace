package com.love.goods.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTreeDTO implements Serializable {
    private Long id;
    private Long pid;
    private String name;
    private String alias;
    private String icon;
    private String slug;
    private Integer level;
    private Integer sortNum;
    private Integer type;
    private String ids;
    private List<CategoryTreeDTO> children;
}
