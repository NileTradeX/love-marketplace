package com.love.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "UserAddressSaveParam")
public class UserAddressSaveParam implements Serializable {

    @Schema(description = "First name", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "first name can not be null")
    private String firstName;

    @Schema(description = "Last Name", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "last name can not be null")
    private String lastName;

    @Schema(description = "Phone Number", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "phoneNumber can not be null")
    private String phoneNumber;

    @Schema(description = "Street Address", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "address can not be null")
    private String address;

    @Schema(description = "City", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "city can not be null")
    private String city;

    @Schema(description = "state", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "state can not be null")
    private String state;

    @Schema(description = "ZIP Code", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "zip code can not be null")
    private String zipCode;

    @Schema(description = "Is Default", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "is default can not be null")
    private Integer isDefault;

    @Schema(description = "Company")
    private String company;
}

