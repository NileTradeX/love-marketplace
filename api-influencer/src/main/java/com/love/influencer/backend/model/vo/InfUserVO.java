package com.love.influencer.backend.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class InfUserVO implements Serializable {
    private Long id;
    private String account;
    private String username;
    private String firstName;
    private String lastName;
    private String code;
    private Integer status;
    private LocalDateTime lastLoginTime;
}
