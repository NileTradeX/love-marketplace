package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class MerUserChangePasswordBO implements Serializable {
    private Long id;
    private String password;
}
