package com.love.merchant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MerUserAdminApproveDTO implements Serializable {
    private String code;
    private String email;
    private String bizName;
}
