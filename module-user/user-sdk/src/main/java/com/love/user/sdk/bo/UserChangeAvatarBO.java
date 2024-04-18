package com.love.user.sdk.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserChangeAvatarBO implements Serializable {
    private Long userId;
    private String avatar;
}
