package com.love.merchant.backend.model.param;

import com.love.common.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "MerPermQueryPageParam")
public class MerPermQueryPageParam extends PageParam {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.AUTO, example = "1")
    private Long id;

    @Schema(description = "title", requiredMode = Schema.RequiredMode.AUTO, example = "savePerm")
    private String title;


}
