package com.love.marketplace.model.param;

import com.love.common.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "OrderQueryPageParam")
public class OrderQueryPageParam extends PageParam {
    @Schema(hidden = true)
    private Long userId;
    @Schema(description = "10:PENDING_SHIPMENT/20:PENDING_RECEIPT/30:AFTER_SALES/40:COMPLETED/50:CLOSED", example = "10")
    private Integer status;
}
