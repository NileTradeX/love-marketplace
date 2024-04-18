package com.love.merchant.backend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MerUserVO implements Serializable {
    private Long id;
    private String account;
    private String username;
    private Integer status;
    private Long roleId;
    private String roleName;
    private Long groupId;
    private LocalDateTime lastLoginTime;
}
