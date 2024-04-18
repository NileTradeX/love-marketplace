package com.love.backend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ReviewVO implements Serializable {
    private Long id;
    private String title;
    private String content;
    private Integer rating;
    private Long userId;
    private Long merchantId;
    private Integer type;
    private Long relatedId;
    private Integer auditStatus;
    private String auditComment;
    private LocalDateTime auditTime;
    private LocalDateTime createTime;
    private Goods goods;

    @Data
    public static class Goods implements Serializable {
        private Long goodsId;
        private Long skuId;
        private String goodsTitle;
        private BigDecimal price;
        private String skuImageUrl;
        private String skuInfo;
    }
}
