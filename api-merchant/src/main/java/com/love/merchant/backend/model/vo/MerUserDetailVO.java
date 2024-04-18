package com.love.merchant.backend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MerUserDetailVO implements Serializable {
    private Long id;
    private String account;
    private String username;
    private Long roleId;
    private String roleName;
    private Integer status;
    @Schema(description = "0(NEED_ONBOARDING) 1(ONBOARDING_IN_PROCESS) 2(COMPLETED)")
    private Integer accountStatus;
    private Long groupId;
    private LocalDateTime lastLoginTime;
}
