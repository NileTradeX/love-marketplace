package com.love.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSimpleDTO implements Serializable {
    private Long id;
    private String title;
    private String affiliateLink;
    private String whiteBgImg;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer onHandStock;
    private Integer salesVolume;
    private Integer status;
    private Long merchantId;
    private LocalDateTime createTime;
    private CategoryDTO firstCategory;
    private CategoryDTO secondCategory;
    private String reviewComment;
    private String subTitle;
    private Integer communityScore;
}
