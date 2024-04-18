package com.love.influencer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfGoodsDTO implements Serializable {
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
