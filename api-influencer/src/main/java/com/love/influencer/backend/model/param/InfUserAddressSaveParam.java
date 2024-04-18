package com.love.influencer.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class InfUserAddressSaveParam implements Serializable {

    @Schema(description = "id", example = "1")
    private Long id;

    @NotNull(message = "Id can not be null")
    @Schema(description = "user id", example = "1", hidden = true)
    private Long userId;

    @NotBlank(message = "country can not be null")
    @Schema(description = "country", example = "US")
    private String country;

    @NotBlank(message = "phoneNumber can not be null")
    @Schema(description = "phoneNumber", example = "12345678")
    private String phoneNumber;

    @NotBlank(message = "city can not be null")
    @Schema(description = "city", example = "New York")
    private String city;

    @NotNull(message = "state can not be null")
    @Schema(description = "state")
    private String state;

    @NotBlank(message = "zipCode can not be null")
    @Schema(description = "zipCode", example = "321123")
    private String zipCode;

    @NotBlank(message = "address can not be null")
    @Schema(description = "address", example = "test")
    private String address;

    private Integer isDefault;
}
