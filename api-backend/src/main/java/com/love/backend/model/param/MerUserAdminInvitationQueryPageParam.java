package com.love.backend.model.param;

import com.love.common.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "MerUserAdminInvitationQueryPageParam")
public class MerUserAdminInvitationQueryPageParam extends PageParam {

    @Schema(description = "bizName")
    private String bizName;

    @Schema(description = "email")
    private String email;

    @Schema(description = "email> 1:submitted,0:unsubmitted")
    private Integer status;
}
