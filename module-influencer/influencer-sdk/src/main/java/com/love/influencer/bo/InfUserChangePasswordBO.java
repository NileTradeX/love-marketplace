package com.love.influencer.bo;

import lombok.Data;

import java.io.Serializable;

@Data
public class InfUserChangePasswordBO implements Serializable {
    private Long id;
    private String password;
}
