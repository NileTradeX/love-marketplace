package com.love.influencer.bo;

import lombok.Data;

import java.io.Serializable;


@Data
public class InfUserResetPasswordBO implements Serializable {
    private Long id;
    private String password;
}
