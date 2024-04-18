package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerUserSaveBO implements Serializable {
    private String account;
    private String username;
    private String password;
    private Long roleId;
    private Long groupId;
}

