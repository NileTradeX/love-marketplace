package com.love.rbac.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserLoginBO implements Serializable {
    private String account;
    private String password;
}
