package com.love.merchant.bo;

import lombok.Data;

import java.io.Serializable;


@Data
public class MerUserLoginBO implements Serializable {
    private String account;
    private String password;
}
