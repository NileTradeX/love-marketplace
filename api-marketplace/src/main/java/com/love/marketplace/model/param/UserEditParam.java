package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;


@Data
@Schema(description = "UserEditParam")
public class UserEditParam implements Serializable {

    @Schema(description = "User ID", hidden = true)
    private Long id;

    @NotBlank(message = "user avatar cannot be null")
    @Schema(description = "User avatar", requiredMode = REQUIRED, example = "dev/20230403/image/43e34a9c87eb0bd2.png")
    private String avatar;

    @NotBlank(message = "First Name cannot be null")
    @Schema(description = "First Name", requiredMode = REQUIRED, example = "John")
    private String firstName;

    @NotBlank(message = "Last Name cannot be null")
    @Schema(description = "Last Name", requiredMode = REQUIRED, example = "Doe")
    private String lastName;

    @Email(message = "email format error")
    @Schema(description = "Email", requiredMode = REQUIRED, example = "john.doe@example.com")
    private String email;
}

