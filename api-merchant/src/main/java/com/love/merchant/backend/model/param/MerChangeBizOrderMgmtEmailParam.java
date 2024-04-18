package com.love.merchant.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Schema(title = "MerChangeBizOrderMgmtEmailParam")
public class MerChangeBizOrderMgmtEmailParam implements Serializable {
    @NotNull(message = "Id can not be null")
    @Schema(description = "id", example = "1", hidden = true)
    private Long id;

    @NotBlank(message = "email cannot be null")
    @Schema(description = "Business Order Management Emil", example = "xxx@gmail.com")
    private String bizOrderMgmtEmail;
}
