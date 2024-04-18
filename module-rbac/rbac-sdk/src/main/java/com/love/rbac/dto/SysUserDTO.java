package com.love.rbac.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SysUserDTO implements Serializable {
    private Long id;
    private String account;
    private String username;
    private Integer status;
    private Integer type;
    private String uid;
    private LocalDateTime lastLoginTime;
    private Long roleId;
    private String roleName;
    private boolean isSuper;
}
