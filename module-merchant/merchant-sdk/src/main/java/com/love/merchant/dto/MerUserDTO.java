package com.love.merchant.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
public class MerUserDTO implements Serializable {
    private Long id;
    private String account;
    private String username;
    private Integer status;
    private Integer type;
    private Long roleId;
    private String roleName;
    private Long groupId;
    private String bizName;
    private String reason;
    private boolean isAdmin;
    private String uid;
    private LocalDateTime lastLoginTime;
}
