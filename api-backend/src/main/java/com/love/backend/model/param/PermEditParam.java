package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PermEditParam implements Serializable {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Id can not be null")
    private Long id;

    @NotNull(message = "flag cannot be null")
    @Schema(description = "menu flag > 1:admin platform 2:merchant platform", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer flag;

    @Schema(description = "title", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotBlank(message = "Title can not be null")
    private String title;

    @Schema(description = "1:dir/2:page/3:button", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Type can not be null")
    private Integer type;

    @Schema(description = "code", requiredMode = Schema.RequiredMode.AUTO, example = "sys:perm:save")
    private String code;

    @Schema(description = "icon", requiredMode = Schema.RequiredMode.AUTO)
    private String icon;

    @NotBlank(message = "Path can not be null")
    @Schema(description = "path", requiredMode = Schema.RequiredMode.REQUIRED)
    private String path;

    @Schema(description = "sortNum", requiredMode = Schema.RequiredMode.AUTO, defaultValue = "1", example = "1")
    private Integer sortNum;

    @Schema(description = "apis", requiredMode = Schema.RequiredMode.REQUIRED, example = "/sys/perm/save,/sys/role/query")
    private String apis;
}
