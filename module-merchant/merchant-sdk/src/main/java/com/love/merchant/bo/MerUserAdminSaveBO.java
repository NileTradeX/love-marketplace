package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerUserAdminSaveBO implements Serializable {
    private Long id;
    private String bizName;
    private String account;
    private String password;
    private MerUserAdminPersonalInfoBO personalInfo;
    private MerUserAdminBusinessInfoEditBO businessInfo;
}
