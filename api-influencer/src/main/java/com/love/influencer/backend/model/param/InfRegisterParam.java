package com.love.influencer.backend.model.param;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class InfRegisterParam implements Serializable {

    @NotNull(message = "id not noll")
    private Long id;
    @NotBlank(message = "password not noll")
    private String password;
    @NotBlank(message = "code not noll")
    private String code;
    @NotBlank(message = "paypalAccount not noll")
    private String paypalAccount;
    @NotBlank(message = "socialLinks not noll")
    private String socialLinks;

    @NotBlank(message = "title not noll")
    private String title;
    @NotBlank(message = "displayName not noll")
    private String displayName;
    @NotBlank(message = "description not noll")
    private String description;
}
