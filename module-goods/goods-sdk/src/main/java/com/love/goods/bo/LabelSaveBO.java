package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LabelSaveBO implements Serializable {
    private Long id;
    private String name;
    /**
     * Label type, 0: ingredient, 1: benefit
     */
    private Integer type;
}
