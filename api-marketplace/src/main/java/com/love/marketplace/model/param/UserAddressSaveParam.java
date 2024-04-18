package com.love.marketplace.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "UserAddressSaveParam")
public class UserAddressSaveParam implements Serializable {

    @NotNull(message = "User ID can not be null")
    @Schema(description = "User ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456", hidden = true)
    private Long userId;

    @NotBlank(message = "First name can not be null")
    @Schema(description = "First name", requiredMode = Schema.RequiredMode.REQUIRED, example = "John")
    private String firstName;

    @NotBlank(message = "Last name can not be null")
    @Schema(description = "Last name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Doe")
    private String lastName;

    @NotBlank(message = "Phone number can not be null")
    @Schema(description = "Phone number", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456789")
    private String phoneNumber;

    @NotBlank(message = "City can not be null")
    @Schema(description = "City", requiredMode = Schema.RequiredMode.REQUIRED, example = "New York")
    private String city;

    @NotNull(message = "State can not be null")
    @Schema(description = "State", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String state;

    @NotBlank(message = "Zip code can not be null")
    @Schema(description = "Zip code", requiredMode = Schema.RequiredMode.REQUIRED, example = "10001")
    private String zipCode;

    @NotBlank(message = "Address can not be null")
    @Schema(description = "Address", requiredMode = Schema.RequiredMode.REQUIRED, example = "123 Main St.")
    private String address;

    @NotNull(message = "isDefault can not be null")
    @Schema(description = "Whether it's the default address", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @Range(min = 0, max = 1, message = "isDefault just can be 0/1")
    private Integer isDefault;

    @Schema(description = "Company name", example = "ABC Inc.")
    private String company;
}

