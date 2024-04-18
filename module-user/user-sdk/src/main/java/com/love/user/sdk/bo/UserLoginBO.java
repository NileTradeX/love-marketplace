package com.love.user.sdk.bo;

import lombok.Data;

import java.io.Serializable;


@Data
public class UserLoginBO implements Serializable {
    private String email;
    private String password;
}
