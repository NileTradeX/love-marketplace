package com.love.user.sdk.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserChangePasswordBO implements Serializable {
    private Long userId;
    private String newPassword;
    private String oldPassword;

}
