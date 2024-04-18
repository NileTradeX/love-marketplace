package com.love.backend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SysUserVO implements Serializable {
    private Long id;
    private String account;
    private String username;
    private Long roleId;
    private String roleName;
    private Integer status;
    private LocalDateTime lastLoginTime;
}
