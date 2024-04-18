package com.love.influencer.backend.model.param;

import com.love.common.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.AUTO;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(title = "GoodsPageQueryParam")
public class GoodsPageQueryParam extends PageParam {

    @Schema(description = "firstCateIds", requiredMode = AUTO)
    private List<Long> firstCateIds;

    @Schema(description = "secondCateIds", requiredMode = AUTO)
    private List<Long> secondCateIds;

    @Schema(description = "Brand ids. support multiple selection", requiredMode = AUTO)
    private List<Long> brandIds;

}
