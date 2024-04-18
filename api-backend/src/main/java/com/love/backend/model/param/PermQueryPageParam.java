package com.love.backend.model.param;

import com.love.common.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;


@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "PermQueryPageParam")
public class PermQueryPageParam extends PageParam {

    @NotNull(message = "flag cannot be null")
    @Schema(description = "menu flag > 1:admin platform 2:merchant platform", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer flag;

    @Schema(description = "pid", requiredMode = Schema.RequiredMode.AUTO, example = "1")
    private Long pid;

    @Schema(description = "title", requiredMode = Schema.RequiredMode.AUTO, example = "savePerm")
    private String title;

}
