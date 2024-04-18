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
public class GoodsReviewDTO implements Serializable {
    private Long id;
    private String title;
    private String whiteBgImg;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer reviewStatus;
    private LocalDateTime createTime;
    private LocalDateTime submissionTime;
    private LocalDateTime reviewTime;
    private String reviewComment;
    private Integer loveScore;
    private String whyLove;
}
