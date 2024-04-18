package com.love.backend.model.param;

import com.love.common.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "MerUserAdminSimpleQueryPageParam")
public class MerUserAdminSimpleQueryPageParam extends PageParam {
    @Schema(description = "account")
    private String account;
}
