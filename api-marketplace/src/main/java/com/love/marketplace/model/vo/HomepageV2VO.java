package com.love.marketplace.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomepageV2VO implements Serializable {
    private String name;
    private String slug;
    private List<GoodsHomepageVO> data;
}
