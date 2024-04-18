package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttrValueSaveBO implements Serializable {
    private Long id;
    private Long attrNameId;
    private String value;
    private Integer sortNum;
}
