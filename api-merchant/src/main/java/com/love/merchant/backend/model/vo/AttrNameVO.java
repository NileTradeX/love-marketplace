package com.love.merchant.backend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AttrNameVO implements Serializable {
    private Long id;
    private String name;
    private List<AttrValueVO> values;
}
