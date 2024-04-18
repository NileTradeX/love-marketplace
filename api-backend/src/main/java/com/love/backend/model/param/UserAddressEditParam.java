package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "UserAddressEditParam")
public class UserAddressEditParam implements Serializable {

    @NotNull(message = "id cannot be null")
    @Schema(description = "Address Id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(description = "First Name")
    private String firstName;

    @Schema(description = "Last Name")
    private String lastName;

    @Schema(description = "Phone Number")
    private String phoneNumber;

    @Schema(description = "Street Address")
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

