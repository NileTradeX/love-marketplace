package com.love.backend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(title = "CustomerVO")
public class CustomerVO implements Serializable {

    @Schema(description = "Customer Id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "CustomerName", requiredMode = Schema.RequiredMode.REQUIRED)
    private String customerName;

    @Schema(description = "Orders", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer orders;

    @Schema(description = "Amount Spent", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal amountSpent;

    @Schema(description = "Registered Date", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
