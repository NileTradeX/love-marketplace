package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Schema(title = "SysRoleSaveParam")
public class SettingPermsParam implements Serializable {

    @Schema(description = "roleId", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Role Id can not by null")
    private Long roleId;

    @Schema(description = "permIds", requiredMode = Schema.RequiredMode.AUTO)
    private List<Long> permIds;

}
