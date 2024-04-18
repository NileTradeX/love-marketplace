package com.love.backend.model.param;

import com.love.common.param.SortParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "MerUserAdminFullQueryPageParam")
public class MerUserAdminFullQueryPageParam extends SortParam {

    @Schema(description = "merchantId")
    private Long id;

    @Schema(description = "account")
    private String account;

    @Schema(description = "status")
    private Integer status;

    @Schema(description = "beginTime")
    private LocalDateTime beginTime;

    @Schema(description = "endTime")
    private LocalDateTime endTime;
}
