package com.love.goods.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BrandDTO implements Serializable {
    private Long id;
    private String name;
    private String logo;
    private Long merchantId;
}
