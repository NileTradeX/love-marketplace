package com.love.backend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(title = "UserAddressVO")
public class UserAddressVO implements Serializable {

    @Schema(description = "address id")
    private Long id;

    @Schema(description = "First name")
    private String firstName;

    @Schema(description = "Last name")
    private String lastName;

    @Schema(description = "Phone number")
    private String phoneNumber;

    @Schema(description = "Street address")
    private String address;

    @Schema(description = "City")
    private String city;

    @Schema(description = "state")
    private String state;

    @Schema(description = "ZIP Code")
    private String zipCode;

    @Schema(description = "Company")
    private String company;

    @Schema(description = "Is default")
    private Integer isDefault;

}

