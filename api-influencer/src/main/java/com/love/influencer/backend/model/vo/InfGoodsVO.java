package com.love.influencer.backend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InfGoodsVO implements Serializable {
    private Long id;
    private Long goodsId;
    private Long influencerId;
    private Integer status;
    private Integer sortNum;
    private Integer salesVolume;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal commissionRate;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String title;
    private String subTitle;
    private Integer communityScore;
    private String whiteBgImg;
    private Integer availableStock;
    private Integer goodsStatus;
    private String slug;
    private String influencerCode;
}
