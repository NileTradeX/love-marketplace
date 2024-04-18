package com.love.rbac.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserSaveBO implements Serializable {
    private String account;
    private String username;
    private String password;
    private Long roleId;
}

