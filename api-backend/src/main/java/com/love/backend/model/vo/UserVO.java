package com.love.backend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(title = "UserVO")
public class UserVO implements Serializable {

    @Schema(description = "User id")
    private Long id;

    @Schema(description = "First Name")
    private String firstName;

    @Schema(description = "Last Name")
    private String lastName;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "Avatar")
    private String avatar;

    @Schema(description = "addressList")
    private List<UserAddressVO> addressList;

    @Schema(description = "notes")
    private String notes;
}
