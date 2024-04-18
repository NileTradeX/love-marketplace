package com.love.marketplace.model.dto;

import com.love.goods.dto.BrandDTO;
import com.love.goods.dto.CategoryDTO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ItemDTO {
    private Long goodsId;
    private String title;
    private String subTitle;
    private Long merchantId;
    private CategoryDTO firstCategory;
    private CategoryDTO secondCategory;
    private BrandDTO brand;
    private String slug;
    private String whiteBgImg;

    private Long skuId;
    private BigDecimal price;
    private BigDecimal shippingWeight;
    private String shippingWeightUnit;
    private Integer qty;
}
