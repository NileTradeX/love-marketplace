package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(title = "CommissionRateSaveParam")
public class CommissionRateSaveParam implements Serializable {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.AUTO)
    private Long id;

    @NotNull(message = "CommissionRate cannot be null")
    @Schema(description = "rate", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal rate;

    @NotNull(message = "effectiveTime cannot be null")
    @Schema(description = "effectiveTime", requiredMode = Schema.RequiredMode.REQUIRED)
    @Future(message = "effectiveTime must be a future time")
    private LocalDateTime effectiveTime;

    @NotNull(message = "merchantId cannot be null")
    @Schema(description = "merchantId", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long merchantId;

    @Schema(description = "bizName", requiredMode = Schema.RequiredMode.AUTO)
    private String bizName;
}
