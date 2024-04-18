package com.love.backend.model.param;

import com.love.common.param.SortParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "UserQueryPageParam")
public class UserQueryPageParam extends SortParam {

    @Schema(description = "Customer Name", example = "Aaron Wang")
    private String customerName;

}
