package com.love.marketplace.model.vo;

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
    private Integer status;
    private LocalDateTime createTime;
    private CategoryVO firstCategory;
    private CategoryVO secondCategory;
    private String reviewComment;
}
