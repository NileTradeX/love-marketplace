package com.love.backend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class GoodsReviewVO implements Serializable {
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
