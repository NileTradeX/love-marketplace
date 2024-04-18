package com.love.goods.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModifyGoodsSkuCommittedStockBO implements Serializable {
    private Long skuId;
    private Integer committedStock;
}
