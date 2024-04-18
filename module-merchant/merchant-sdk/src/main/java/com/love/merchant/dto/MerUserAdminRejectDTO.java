package com.love.merchant.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerUserAdminRejectDTO implements Serializable {
    private String code;
    private String email;
    private String bizName;
}
