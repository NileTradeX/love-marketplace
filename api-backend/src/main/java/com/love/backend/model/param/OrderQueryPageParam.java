package com.love.backend.model.param;

import com.love.common.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "OrderQueryPageParam")
public class OrderQueryPageParam extends PageParam {

    @Schema(description = "orderNo", example = "O20230405123456")
    private String orderNo;

    @Schema(description = "merchantId", example = "1")
    private Long merchantId;

    @Schema(description = "10:PENDING_SHIPMENT/20:PENDING_RECEIPT/30:AFTER_SALES/40:COMPLETED/50:CLOSED", example = "10")
    private Integer status;

    @Schema(description = "goodsId", example = "1")
    private Long goodsId;

    @Schema(description = "goodsTitle", example = "mind soul")
    private String goodsTitle;

    @Schema(description = "trackingNo", example = "U0000")
    private String trackingNo;

    @Schema(description = "skuId", example = "1")
    private Long skuId;

    @Schema(description = "consignee", example = "Evan Chen")
    private String consignee;

    @Schema(description = "consigneePhone", example = "13256789900")
    private String consigneePhone;

    @Schema(description = "beginTime", example = "2023-02-02 12:00:00")
    private LocalDateTime beginTime;

    @Schema(description = "endTime", example = "2023-02-02 12:00:00")
    private LocalDateTime endTime;

    @Schema(description = "1: recent 6 months ; 2: 6 months ago", example = "1")
    private Integer timeRange;
}
