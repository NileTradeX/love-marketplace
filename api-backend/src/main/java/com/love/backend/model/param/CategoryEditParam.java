package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "CategoryEditParam")
public class CategoryEditParam implements Serializable {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "category id can not be null")
    private Long id;

    @Schema(description = "name", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "category name can not be null")
    private String name;

    @Schema(description = "alias", requiredMode = Schema.RequiredMode.AUTO)
    private String alias;

    @Schema(description = "icon", requiredMode = Schema.RequiredMode.AUTO)
    private String icon;

    @Schema(description = "pid", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "category pid can not be null")
    private Long pid = 0L;

    @Schema(description = "level", requiredMode = Schema.RequiredMode.AUTO, defaultValue = "1")
    private Integer level = 1;

    @Schema(description = "sortNum", requiredMode = Schema.RequiredMode.AUTO, defaultValue = "1")
    private Integer sortNum = 1;

    @Schema(description = "type", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "category type can not be null")
    private Integer type;

    @Schema(description = "ids", requiredMode = Schema.RequiredMode.AUTO)
    private String ids;

    @Schema(description = "oldPid", requiredMode = Schema.RequiredMode.AUTO)
    private Long oldPid;
}
