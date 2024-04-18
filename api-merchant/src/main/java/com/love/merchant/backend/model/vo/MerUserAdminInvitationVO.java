package com.love.merchant.backend.model.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerUserAdminInvitationVO implements Serializable {
    private Long id;
    private String bizName;
    private String email;
    private String code;
    private String status;
    private MerUserAdminFullVO adminInfo;
}
