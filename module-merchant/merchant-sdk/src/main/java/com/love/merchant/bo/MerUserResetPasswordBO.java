package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;


@Data
public class MerUserResetPasswordBO implements Serializable {
    private Long id;
    private String password;
}
