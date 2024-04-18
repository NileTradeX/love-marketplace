package com.love.backend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(title = "MerUserAdminInvitationVO")
public class MerUserAdminInvitationVO implements Serializable {
    private Long id;
    private String bizName;
    private String email;
    private String code;
    private Integer status;
    private LocalDateTime createTime;
    private BigDecimal commissionFeeRate;
    private String mpa;
    private LocalDateTime updateTime;
}
