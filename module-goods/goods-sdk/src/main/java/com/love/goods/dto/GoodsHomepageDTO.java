package com.love.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsHomepageDTO implements Serializable {
    private Long id;
    private String title;
    private String subTitle;
    private String whiteBgImg;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer communityScore;
    private Integer loveScore;
    private Long availableStock;
}
