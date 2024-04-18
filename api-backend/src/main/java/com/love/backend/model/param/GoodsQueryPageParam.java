package com.love.backend.model.param;

import com.love.common.param.PageParam;
import com.love.goods.enums.GoodsType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.AUTO;

@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsQueryPageParam extends PageParam {

    @Schema(description = "goods id", requiredMode = AUTO, example = "0")
    private Long id;

    @Schema(description = "goods title", requiredMode = AUTO, example = "title")
    private String title;

    @Schema(description = "Merchant id(which merchant you want to search)", requiredMode = AUTO, example = "1")
    private Long merchantId;

    @Schema(description = "first category id", requiredMode = AUTO, example = "1")
    private Long firstCateId;

    @Schema(description = "second category id", requiredMode = AUTO, example = "1")
    private Long secondCateId;

    @Schema(description = "Goods status", requiredMode = AUTO, example = "0")
    private Integer status;

    @Schema(description = "min price", requiredMode = AUTO, example = "00.00")
    private BigDecimal minPrice;

    @Schema(description = "max price", requiredMode = AUTO, example = "00.00")
    private BigDecimal maxPrice;

    @Schema(description = "start create date", requiredMode = AUTO, example = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startCreationDate;

    @Schema(description = "end create date", requiredMode = AUTO, example = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endCreationDate;

    @Schema(description = "min sales volume", requiredMode = AUTO, example = "0")
    private Integer minSalesVolume;

    @Schema(description = "max sales volume", requiredMode = AUTO, example = "0")
    private Integer maxSalesVolume;

    @Schema(description = "Goods type > 1:physical 2:digital", requiredMode = AUTO, example = "1", defaultValue = "1")
    private Integer type = GoodsType.PHYSICAL.getType();
}
