package com.love.goods.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("m_goods")
public class Goods implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long merchantId;
    private Long brandId;
    private Long firstCateId;
    private Long secondCateId;
    private String title;
    private String subTitle;
    private String affiliateLink;
    private String keyIngredients;
    private String ingredientsJson;
    private String descText;
    private String descWarnings;
    private String intro;
    private String video;
    private String mainImages;
    private String whiteBgImg;
    private String detailImages;
    private Integer status;
    private Long shippingRatesTemplateId;
    private String certifications;
    private Integer communityScore;
    private Integer type;
    private String reviewComment;
    private Integer reviewStatus;
    private LocalDateTime reviewTime;
    private LocalDateTime submissionTime;
    private Integer onHandStock;
    private Integer salesVolume;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Integer loveScore;
    private String whyLove;
    private String searchPageTitle;
    private String searchMetaDescription;
    private Integer organic;
    private String sustainability;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    private Integer deleted;
}
