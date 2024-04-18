package com.love.marketplace.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BrandVO implements Serializable {
    private Long id;
    private String name;
    private String logo;
    private String slug;
}
