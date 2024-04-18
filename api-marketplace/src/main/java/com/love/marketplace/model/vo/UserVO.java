package com.love.marketplace.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Schema(title = "UserVO")
public class UserVO implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "First Name")
    private String firstName;

    @Schema(description = "Last Name")
    private String lastName;

    @Schema(description = "Birthday")
    private LocalDate birthday;

    @Schema(description = "Avatar")
    private String avatar;
}
