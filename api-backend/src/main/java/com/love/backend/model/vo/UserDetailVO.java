package com.love.backend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Schema(title = "UserDetailVO")
public class UserDetailVO implements Serializable {

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

    @Schema(description = "Notes")
    private String notes;

    @Schema(description = "Orders")
    private Integer orders;

    @Schema(description = "Amount Spent")
    private BigDecimal amountSpent;

    @Schema(description = "Create time")
    private LocalDateTime createTime;

    @Schema(description = "Address list")
    private List<UserAddressVO> addressList;

}
