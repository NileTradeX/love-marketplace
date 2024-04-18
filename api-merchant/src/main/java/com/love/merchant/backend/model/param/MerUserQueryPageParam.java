package com.love.merchant.backend.model.param;

import com.love.common.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "MerUserQueryPageParam")
public class MerUserQueryPageParam extends PageParam {

    @Schema(description = "account", requiredMode = Schema.RequiredMode.AUTO, example = "ruoran")
    private String account;

    @Schema(description = "username", requiredMode = Schema.RequiredMode.AUTO, example = "evan chen")
    private String username;
}
