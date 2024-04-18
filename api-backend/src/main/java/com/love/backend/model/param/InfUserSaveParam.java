package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Schema(title = "InfUserSaveParam")
public class InfUserSaveParam implements Serializable {

    @Schema(description = "account", requiredMode = Schema.RequiredMode.REQUIRED, example = "xxx@xyz.com")
    @NotNull(message = "account can not be null")
    private String account;

    @Schema(description = "firstName", requiredMode = Schema.RequiredMode.REQUIRED, example = "john")
    @NotNull(message = "firstName can not be null")
    private String firstName;

    @Schema(description = "lastName", requiredMode = Schema.RequiredMode.REQUIRED, example = "green")
    @NotNull(message = "lastName can not be null")
    private String lastName;

    @Schema(description = "commissionRate", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "commissionRate can not be null")
    private BigDecimal commissionRate;
}
