package com.love.marketplace.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GoodsVO implements Serializable {
    private Long id;
    private String title;
    private String subTitle;
    private Long merchantId;
    private CategoryVO category;
    private String affiliateLink;
    private BrandVO brand;
    private List<LabelVO> keyIngredients;
    private String servingSize;
    private String servingPerContainer;
    private List<IngredientVO> ingredients;
    private String descText;
    private String descWarnings;
    private List<LabelVO> benefits;
    private String intro;
    private String video;
    private String mainImages;
    private String whiteBgImg;
    private String detailImages;
    private List<AttrNameVO> variants;
    private String defaultSkuId;
    private List<GoodsSkuVO> skus;
    private Long shippingRatesTemplateId;
    private Integer type;
    private String certifications;
    private Integer communityScore;
    private Integer loveScore;
    private String whyLove;
    private String slug;
    private GoodsDocVO goodsDoc;
    private CategoryVO firstCategory;
    private CategoryVO secondCategory;
    private Integer shippingModels;
    private Integer status;
    private Integer organic;
    private String sustainability;
}
