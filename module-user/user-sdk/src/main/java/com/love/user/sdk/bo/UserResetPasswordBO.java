package com.love.user.sdk.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserResetPasswordBO implements Serializable {
    private Long id;
    private String password;
}
