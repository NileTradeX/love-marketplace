package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AttrNameSaveBO implements Serializable {
    private Long id;
    private String name;
    private List<AttrValueSaveBO> values;
}
