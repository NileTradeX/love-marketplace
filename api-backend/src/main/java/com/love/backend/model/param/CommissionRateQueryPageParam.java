package com.love.backend.model.param;

import com.love.common.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "CommissionRateQueryPageParam")
public class CommissionRateQueryPageParam extends PageParam {

    @Schema(description = "bizName", requiredMode = Schema.RequiredMode.AUTO)
    private String bizName;

    @Schema(description = "minRate", requiredMode = Schema.RequiredMode.AUTO)
    private String minRate;

    @Schema(description = "maxRate", requiredMode = Schema.RequiredMode.AUTO)
    private String maxRate;
}
