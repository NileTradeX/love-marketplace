package com.love.goods.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RecommendGoodsDTO implements Serializable {
    private Long id;
    private String title;
    private String subTitle;
    private String whiteBgImg;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer communityScore;
    private Integer loveScore;
    private String slug;
}
