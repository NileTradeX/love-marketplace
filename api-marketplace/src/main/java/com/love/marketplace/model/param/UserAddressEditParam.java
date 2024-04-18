package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "UserAddressEditParam")
public class UserAddressEditParam implements Serializable {

    @NotNull(message = "Id can not be null")
    @Schema(description = "Id")
    private Long id;

    @NotBlank(message = "First name can not be null")
    @Schema(description = "First name")
    private String firstName;

    @NotBlank(message = "Last name can not be null")
    @Schema(description = "Last name")
    private String lastName;

    @NotBlank(message = "City can not be null")
    @Schema(description = "City")
    private String city;

    @NotNull(message = "State can not be null")
    @Schema(description = "State")
    private String state;

    @NotBlank(message = "Zip Code can not be null")
    @Schema(description = "Zip Code")
    private String zipCode;

    @NotBlank(message = "phoneNumber can not be null")
    @Schema(description = "phoneNumber")
    private String phoneNumber;

    @Schema(description = "Company")
    private String company;

    @NotBlank(message = "Address can not be null")
    @Schema(description = "Address")
    private String address;

    @NotNull(message = "isDefault can not be null")
    @Schema(description = "Default address or not. yes:1/no:0")
    @Range(min = 0, max = 1, message = "isDefault just can be 0/1")
    private Integer isDefault;

    @NotNull(message = "userId can not be null")
    private Long userId;
}
