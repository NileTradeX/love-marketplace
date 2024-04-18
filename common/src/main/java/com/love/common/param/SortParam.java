package com.love.common.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SortParam extends PageParam {

    @Schema(description = "sort", requiredMode = Schema.RequiredMode.AUTO, example = "id_asc/id_desc")
    private String sort;
}
