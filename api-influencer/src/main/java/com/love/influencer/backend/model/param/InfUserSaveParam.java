package com.love.influencer.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Schema(title = "InfluencerUserSaveParam")
public class InfUserSaveParam implements Serializable {

    @Schema(description = "account", requiredMode = REQUIRED, example = "evan@love.com", defaultValue = "evan@love.com")
    @Length(max = 32, message = "Account length must less than 32")
    private String account;

    @Schema(description = "username", requiredMode = REQUIRED, example = "evan chen", defaultValue = "evan chen")
    @Length(max = 32, message = "Username length must less than 32")
    private String username;

    @Schema(description = "password", requiredMode = REQUIRED, example = "66668888", defaultValue = "66668888")
    @Length(min = 8, max = 32, message = "Password length must between 8 and 32")
    private String password;

    @Schema(description = "firstName", example = "evan", defaultValue = "evan")
    private String firstName;

    @Schema(description = "lastName", example = "chen", defaultValue = "chen")
    private String lastName;
}
