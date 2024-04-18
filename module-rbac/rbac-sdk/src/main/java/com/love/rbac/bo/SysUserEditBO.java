package com.love.rbac.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserEditBO implements Serializable {
    private Long id;
    private String username;
    private Long roleId;
}
