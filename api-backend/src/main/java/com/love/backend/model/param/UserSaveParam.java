package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.AUTO;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "UserSaveParam")
public class UserSaveParam implements Serializable {

    @Schema(description = "Avatar", requiredMode = AUTO, example = "dev/20230403/image/43e34a9c87eb0bd2.png")
    private String avatar;

    @NotBlank(message = "First name can not be null")
    @Length(max = 20, message = "First name length must less than 20")
    @Schema(description = "First Name", requiredMode = REQUIRED, example = "Aaron")
    private String firstName;

    @NotBlank(message = "Last name can not be null")
    @Schema(description = "Last name", requiredMode = REQUIRED, example = "Wang")
    private String lastName;

    @NotBlank(message = "Password can not be null")
    @Schema(description = "Initial Password", requiredMode = REQUIRED, example = "P@ssw0rd123")
    private String password;

    @NotBlank(message = "Email can not be null")
    @Email(message = "email format error")
    @Schema(description = "Email", requiredMode = REQUIRED, example = "aaron@love.com")
    private String email;

    @Schema(description = "notes")
    @Length(max = 5000, message = "Notes length must less than 5000")
    private String notes;

    @Schema(description = "addressList")
    @Size(max = 5, message = "Number of added addresses exceeding the maximum limit of 5")
    private List<@Valid UserAddressSaveParam> addressList;
}

