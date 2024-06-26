package com.love.goods.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDetailQueryBO implements Serializable {
    private Long id;
    private Integer skuStatus;
    private List<Integer> statusList;
}
