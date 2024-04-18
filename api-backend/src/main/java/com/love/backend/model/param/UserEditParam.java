package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.AUTO;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "UserEditParam")
public class UserEditParam implements Serializable {

    @NotNull(message = "User id can not be null")
    @Schema(description = "id", requiredMode = REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Avatar", requiredMode = AUTO)
    private String avatar;

    @NotBlank(message = "First name can not be null")
    @Length(max = 20, message = "First name length must less than 20")
    @Schema(description = "First name", requiredMode = REQUIRED, example = "Aaron")
    private String firstName;

    @NotBlank(message = "Last name can not be null")
    @Length(max = 20, message = "Last name length must less than 20")
    @Schema(description = "Last name", requiredMode = REQUIRED, example = "Wang")
    private String lastName;

    @NotBlank(message = "Email can not be null")
    @Length(max = 32, message = "Email length must less than 32")
    @Email(message = "Email format error")
    @Schema(description = "Email", requiredMode = REQUIRED, example = "aaron@love.com")
    private String email;

    @Schema(description = "Notes")
    @Length(max = 5000, message = "Notes length must less than 5000")
    private String notes;

    @Schema(description = "addressList")
    private List<UserAddressEditParam> addressList;

}
