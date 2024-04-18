package com.love.goods.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ModifyGoodsSkuAvailableStockBO implements Serializable {
    private List<ModifySkuAvailableStockBO> modifySkuAvailableStockList;
}
