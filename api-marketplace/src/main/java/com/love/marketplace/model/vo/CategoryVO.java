package com.love.marketplace.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO implements Serializable {
    private Long id;
    private Long pid;
    private String name;
    private String icon;
    private Integer level;
    private String slug;
    private Integer sortNum;
    private Integer type;
    private String ids;
}
