package com.love.influencer.bo;

import lombok.Data;

import java.io.Serializable;


@Data
public class InfUserLoginBO implements Serializable {
    private String account;
    private String password;
}
