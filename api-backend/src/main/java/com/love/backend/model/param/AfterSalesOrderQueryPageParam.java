package com.love.backend.model.param;

import com.love.common.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "AfterSalesOrderQueryPageParam")
public class AfterSalesOrderQueryPageParam extends PageParam {
    @Schema(hidden = true)
    private Long userId;

    @Schema(description = "merchantId")
    private Long merchantId;

    @Schema(description = "merOrderNo")
    private String merOrderNo;

    @Schema(description = "afterSaleNo")
    private String afterSaleNo;

    @Schema(description = "startTime")
    private String startTime;

    @Schema(description = "endTime")
    private String endTime;
}
