package com.love.backend.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AttrValueVO implements Serializable {
    private Long id;
    private Long attrNameId;
    private String value;
    private Integer sortNum;
}
