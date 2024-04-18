package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema(title = "MerUserQueryByAccountParam")
public class MerUserQueryByAccountParam implements Serializable {
    @NotBlank(message = "Email can not be blank")
    @Length(max = 32, message = "Email length must less than 32")
    @Email(message = "email format error")
    @Schema(description = "email", example = "aaron@love.com")
    private String account;
}
