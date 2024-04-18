package com.love.merchant.backend.model.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class MerUserAdminInvitationQueryByCodeParam implements Serializable {
    @NotBlank(message = "code cannot be null")
    private String code;
}
