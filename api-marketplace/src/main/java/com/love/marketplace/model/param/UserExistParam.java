package com.love.marketplace.model.param;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserExistParam implements Serializable {

    @NotBlank(message = "email cannot be blank")
    @Email(message = "email format error")
    private String email;
}
