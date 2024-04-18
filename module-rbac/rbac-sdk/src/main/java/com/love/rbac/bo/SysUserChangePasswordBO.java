package com.love.rbac.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserChangePasswordBO implements Serializable {
    private Long id;
    private String password;
}
