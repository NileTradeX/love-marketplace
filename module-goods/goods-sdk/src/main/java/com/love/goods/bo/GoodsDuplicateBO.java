package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class GoodsDuplicateBO implements Serializable {
    private Long id;
    private String name;
}
