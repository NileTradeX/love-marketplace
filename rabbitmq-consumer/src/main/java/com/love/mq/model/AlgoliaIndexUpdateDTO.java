package com.love.mq.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Getter
@Setter
public class AlgoliaIndexUpdateDTO implements Serializable {
    private String objectID;
    private Long id;
    private String title;
    private String subTitle;
    private String descText;
    private String descWarnings;
    private String intro;
    private Integer communityScore;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer loveScore;
    private String whiteBgImg;
    private String slug;
    private Long firstCateId;
    private String firstCateName;
    private Long secondCateId;
    private String secondCateName;
    private Long brandId;
    private String brandName;
    private Long updateTime;
    private Integer salesVolume;
}
