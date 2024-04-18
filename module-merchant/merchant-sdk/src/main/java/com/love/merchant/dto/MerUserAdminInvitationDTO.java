package com.love.merchant.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MerUserAdminInvitationDTO implements Serializable {
    private Long id;
    private String bizName;
    private String email;
    private String code;
    private Integer status;
    private Integer commissionFeeRate;
    private String mpa;
    private LocalDateTime createTime;
    private MerUserAdminDTO adminInfo;
    private LocalDateTime updateTime;
}
