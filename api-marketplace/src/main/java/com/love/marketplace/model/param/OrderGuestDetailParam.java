package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Schema(title = "OrderGuestDetailParam")
public class OrderGuestDetailParam implements Serializable {

    @Schema(description = "merOrderNo", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "merOrderNo cannot be blank")
    private String merOrderNo;

    @Schema(description = "email", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "email cannot be blank")
    @Length(max = 64, message = "Email length must less than 64")
    @Email(message = "email format error")
    private String email;
}
