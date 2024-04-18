package com.love.rbac.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleDTO implements Serializable {
    private Long id;
    private String name;
}
