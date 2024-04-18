package com.love.user.sdk.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class GuestDTO implements Serializable {
    private Long id;

    private String email;
}
