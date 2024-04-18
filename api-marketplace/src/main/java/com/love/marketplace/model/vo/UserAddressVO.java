package com.love.marketplace.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(title = "UserAddressVO")
public class UserAddressVO implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "User Id")
    private Long userId;

    @Schema(description = "First Name")
    private String firstName;

    @Schema(description = "Last Name")
    private String lastName;

    @Schema(description = "Phone Number")
    private String phoneNumber;

    @Schema(description = "City")
    private String city;

    @Schema(description = "State")
    private String state;

    @Schema(description = "Zip Code")
    private String zipCode;

    @Schema(description = "Company")
    private String company;

    @Schema(description = "Street Address")
    private String address;

    @Schema(description = "Default address or not. yes:1/no:0")
    private Integer isDefault;
}
