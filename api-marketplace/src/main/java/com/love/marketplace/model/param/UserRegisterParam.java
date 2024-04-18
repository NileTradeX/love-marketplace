package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "UserRegisterParam")
public class UserRegisterParam implements Serializable {

    @NotBlank(message = "Email can not be null")
    @Email(message = "email format error")
    @Schema(description = "Email", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "Password can not be null")
    @Schema(description = "Password", example = "P@ssword123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @NotBlank(message = "First name can not be null")
    @Schema(description = "First name", example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
    private String firstName;

    @NotBlank(message = "Last name can not be null")
    @Schema(description = "Last name", example = "Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    private String lastName;

    @NotNull(message = "Accepts terms 0f service can not be null")
    @Range(max = 1, min = 1, message = "You must agree to Love's Terms of Use")
    @Schema(description = "Accepts Terms Of Service, Yes:1/No:0", example = "1")
    private Integer acceptsTermsOfService;

    @Range(max = 1, min = 0, message = "Subscribe To Newsletter must be 0 or 1")
    @Schema(description = "Subscribe To Newsletter, Yes:1/No:0", example = "0")
    private Integer subscribeToNewsletter;

    @Schema(description = "User Address")
    private UserAddressSaveParam address;
}
