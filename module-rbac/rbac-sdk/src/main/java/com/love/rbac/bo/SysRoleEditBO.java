package com.love.rbac.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysRoleEditBO implements Serializable {
    private Long id;
    private String name;
}
