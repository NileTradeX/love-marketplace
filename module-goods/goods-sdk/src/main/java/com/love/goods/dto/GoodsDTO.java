package com.love.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoodsDTO implements Serializable {
    private Long id;
    private String title;
    private String subTitle;
    private Long merchantId;
    private Long firstCateId;
    private Long secondCateId;
    private CategoryDTO firstCategory;
    private CategoryDTO secondCategory;
    private String affiliateLink;
    private BrandDTO brand;
    private List<LabelDTO> keyIngredients;
    private String servingSize;
    private String servingPerContainer;
    private List<IngredientDTO> ingredients;
    private String descText;
    private String descWarnings;
    private String intro;
    private Integer status;
    private String video;
    private String mainImages;
    private String whiteBgImg;
    private String detailImages;
    private List<AttrNameDTO> variants;
    private List<GoodsSkuDTO> skus;
    private Long shippingRatesTemplateId;
    private Integer type;
    private String certifications;
    private Integer communityScore;
    private Integer loveScore;
    private String whyLove;
    private String slug;
    private String searchPageTitle;
    private String searchMetaDescription;
    private GoodsDocDTO goodsDoc;
    private Integer salesVolume;
    private Integer organic;
    private String sustainability;
}
