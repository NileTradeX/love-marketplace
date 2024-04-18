package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(title = "PhysicalGoodsSaveParam")
public class PhysicalGoodsSaveParam implements Serializable {

    @Schema(description = "Product id", requiredMode = AUTO, example = "id")
    private Long id;

    @NotNull(message = "Merchant id can't be null")
    @Schema(description = "merchant id", requiredMode = REQUIRED, example = "merchantId")
    private Long merchantId;

    @NotBlank(message = "Product title can't be empty")
    @Schema(description = "Product title(or product name)", requiredMode = REQUIRED, example = "title")
    private String title;

    @NotBlank(message = "Product sub title can't be empty")
    @Schema(description = "Product sub title", requiredMode = REQUIRED, example = "subTitle")
    private String subTitle;

    @NotNull(message = "firstCateId can't be null")
    @Schema(description = "first category id", requiredMode = REQUIRED, example = "1")
    private Long firstCateId;

    @Schema(description = "second category id", requiredMode = AUTO, example = "1")
    private Long secondCateId;

    @NotNull(message = "Brand id can't be null")
    @Schema(description = "brand id", requiredMode = REQUIRED, example = "1")
    private Long brandId;

    @Schema(description = "key ingredients", requiredMode = AUTO)
    private List<@Valid LabelSaveParam> keyIngredients;

    @Schema(description = "serving size", requiredMode = AUTO, example = "serving size")
    private String servingSize;

    @Schema(description = "serving Per Container", requiredMode = AUTO, example = "serving Per Container")
    private String servingPerContainer;

    @Schema(description = "ingredients", requiredMode = AUTO)
    private List<@Valid IngredientParam> ingredients;

    @NotBlank(message = "Description body text can't be empty")
    @Schema(description = "Description body text", requiredMode = REQUIRED, example = "Description body text")
    private String descText;

    @Schema(description = "Description Warnings", requiredMode = AUTO, example = "Description Warnings")
    private String descWarnings;

    @NotBlank(message = "How to use can't be empty")
    @Schema(description = "How to use(in page)", requiredMode = REQUIRED, example = "How to use")
    private String intro;

    @NotBlank(message = "Main images can't be empty")
    @Schema(description = "Main images, comma split image keys", requiredMode = REQUIRED, example = "key1,key2,key3")
    private String mainImages;

    @NotBlank(message = "White background image can't be empty")
    @Schema(description = "white background image", requiredMode = REQUIRED, example = "key1")
    private String whiteBgImg;

    @Schema(description = "product short video", requiredMode = AUTO)
    private String video;

    @Schema(description = "Detail images, comma split image keys", requiredMode = AUTO, example = "key1,key2,key3")
    private String detailImages;

    @NotEmpty(message = "Variants can't be empty")
    @Schema(description = "Variants", requiredMode = REQUIRED)
    private List<AttrNameSaveParam> variants;

    @NotEmpty(message = "Sku can't be empty")
    @Schema(description = "skus", requiredMode = REQUIRED)
    private List<@Valid GoodsSkuSaveParam> skus;

    @Schema(description = "Shipping rates template id", requiredMode = AUTO, example = "shippingRatesTemplateId")
    private Long shippingRatesTemplateId;

    @Schema(description = "Certifications, Comma split ids", requiredMode = AUTO, example = "key1,key2,key3")
    private String certifications;

    @Schema(description = "Expert Rating Score", requiredMode = AUTO, example = "100")
    private Integer communityScore;

    @NotNull(message = "Goods type can't be null")
    @Schema(description = "Goods type > 1:physical 2:digital", requiredMode = REQUIRED, example = "0")
    private Integer type;

    @Size(max = 70)
    @Schema(description = "Search Page Title", maximum = "70", requiredMode = NOT_REQUIRED, example = "Product Name")
    private String searchPageTitle;

    @Size(max = 320)
    @Schema(description = "Search Meta Description", maximum = "320", requiredMode = NOT_REQUIRED, example = "Product Description")
    private String searchMetaDescription;

    @Builder.Default
    @Schema(description = "is publish or not, default not publish", requiredMode = AUTO, example = "false")
    private boolean publish = false;

    @Schema(description = "GoodsDoc", requiredMode = AUTO)
    private @Valid GoodsDoc goodsDoc;

    @Schema(description = "organic: 1>organic, 2>Partial organic, 3>All natural", requiredMode = AUTO)
    private Integer organic;

    @Schema(description = "sustainability: 1>No plastic, 2>Recycled materials", requiredMode = AUTO)
    private String sustainability;

    @Data
    public static class GoodsDoc implements Serializable {
        private String coa;
        private Integer coaStatus;
        private String msds;
        private Integer msdsStatus;
        private String ppp;
        private Integer pppStatus;
        private String logfm;
        private Integer logfmStatus;
        private String rein;
        private Integer reinStatus;
    }
}
