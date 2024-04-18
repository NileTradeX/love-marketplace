package com.love.goods.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublishCategoryQueryBO {
    private List<Long> firstCateIds;
    private List<Long> secondCateIds;
}
