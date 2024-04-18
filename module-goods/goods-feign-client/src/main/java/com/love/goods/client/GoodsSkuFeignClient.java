package com.love.goods.client;

import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.goods.bo.ModifyGoodsSkuAvailableStockBO;
import com.love.goods.bo.ModifyGoodsSkuCommittedStockBO;
import com.love.goods.dto.GoodsSkuDTO;
import com.love.goods.dto.GoodsSkuSimpleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "goods-service-api", contextId = "skuFeignClient", path = "goods/sku")
public interface GoodsSkuFeignClient {

    @GetMapping("queryById")
    GoodsSkuDTO detail(@SpringQueryMap IdParam idParam);

    @GetMapping("modifyCommittedStock")
    Boolean modifyCommittedStock(@SpringQueryMap ModifyGoodsSkuCommittedStockBO modifyGoodsSkuCommittedStockBO);

    @PostMapping("modifyAvailableStock")
    Boolean modifyAvailableStock(ModifyGoodsSkuAvailableStockBO modifyGoodsSkuAvailableStockBO);

    @GetMapping("simple")
    GoodsSkuSimpleDTO simple(@SpringQueryMap IdParam idParam);

    @GetMapping("queryAvailableStockByGoodsId")
    Integer queryAvailableStockByGoodsId(@SpringQueryMap IdParam idParam);

    @GetMapping("queryAvailableStockByGoodsIds")
    Map<Integer, Long> queryAvailableStockByGoodsIds(@SpringQueryMap IdsParam idsParam);

    @GetMapping("querySkuSpecs")
    Map<Long, String> querySkuSpecs(@SpringQueryMap IdsParam idsParam);

    @GetMapping("queryByIds")
    List<GoodsSkuDTO> queryByIds(@SpringQueryMap IdsParam idsParam);
}
