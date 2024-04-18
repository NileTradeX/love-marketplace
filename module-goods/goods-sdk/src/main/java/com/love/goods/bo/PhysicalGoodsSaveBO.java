package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PhysicalGoodsSaveBO implements Serializable {
    private Long id;
    private String title;
    private String subTitle;
    private Long merchantId;
    private Long firstCateId;
    private Long secondCateId;
    private Long brandId;
    private List<LabelSaveBO> keyIngredients;
    private String servingSize;
    private String servingPerContainer;
    private List<IngredientBO> ingredients;
    private String descText;
    private String descWarnings;
    private String intro;
    private String video;
    private String mainImages;
    private String whiteBgImg;
    private String detailImages;
    private List<AttrNameSaveBO> variants;
    private List<GoodsSkuSaveBO> skus;
    private Long shippingRatesTemplateId;
    private String certifications;
    private Integer communityScore;
    private Integer type;
    private String searchPageTitle;
    private String searchMetaDescription;
    private GoodsDocSaveBO goodsDoc;
    private boolean publish = false;
    private Integer organic;
    private String sustainability;
}

