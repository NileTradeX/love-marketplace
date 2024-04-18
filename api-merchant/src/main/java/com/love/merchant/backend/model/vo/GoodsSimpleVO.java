package com.love.merchant.backend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class GoodsSimpleVO implements Serializable {
    private Long id;
    private String title;
    private String whiteBgImg;
    private String affiliateLink;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer onHandStock;
    private Integer salesVolume;
    @Schema(description = "Goods status 0: Draft, 1: Under Review, 2: Rejected 3: On Sales 4: Delisted")
    private Integer status;
    private LocalDateTime createTime;
    private CategoryVO firstCategory;
    private CategoryVO secondCategory;
    private String reviewComment;
}
