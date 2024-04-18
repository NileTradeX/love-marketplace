package com.love.influencer.backend.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class InfGoodsPageQueryParam implements Serializable {
    private int pageNum = 1;
    private int pageSize = 10;
    @NotNull(message = "Id can not be null")
    @Schema(description = "user id", example = "1", hidden = true)
    private Long userId;
    private List<Long> firstCateIds;
    private List<Long> secondCateIds;
    private List<Long> brandIds;
}
