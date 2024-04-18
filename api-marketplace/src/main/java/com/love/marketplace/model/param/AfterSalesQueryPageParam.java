package com.love.marketplace.model.param;

import com.love.common.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "AfterSalesQueryPageParam")
public class AfterSalesQueryPageParam extends PageParam {
    @Schema(hidden = true)
    private Long userId;
}
