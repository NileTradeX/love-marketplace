package com.love.merchant.backend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GoodsVO implements Serializable {
    private Long id;
    private String title;
    private String subTitle;
    private Long merchantId;
    private CategoryVO firstCategory;
    private CategoryVO secondCategory;
    private String affiliateLink;
    private BrandVO brand;
    private List<LabelVO> keyIngredients;
    private String servingSize;
    private String servingPerContainer;
    private List<IngredientVO> ingredients;
    private String descText;
    private String descWarnings;
    private String video;
    private String intro;
    private Integer status;
    private String mainImages;
    private String whiteBgImg;
    private String detailImages;
    private List<AttrNameVO> variants;
    private List<GoodsSkuVO> skus;
    private Long shippingRatesTemplateId;
    private String certifications;
    private Integer communityScore;
    @Schema(description = "Goods type( 1: Physical goods, 2: Digital goods)")
    private Integer type;
    private String searchPageTitle;
    private String searchMetaDescription;
    private String slug;
    private GoodsDocVO goodsDoc;
    private Integer organic;
    private String sustainability;
}
