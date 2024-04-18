package com.love.marketplace.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(title = "LoginUserVO")
public class LoginUserVO implements Serializable {

    @Schema(description = "User Id")
    private Long id;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "First Name")
    private String firstName;

    @Schema(description = "Last Name")
    private String lastName;

    @Schema(description = "Avatar")
    private String avatar;

    @Schema(description = "token")
    private String token;

    @Schema(description = "source")
    private Integer source;

}
