package com.love.goods.controller;


import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.common.result.Result;
import com.love.goods.bo.ModifyGoodsSkuAvailableStockBO;
import com.love.goods.bo.ModifyGoodsSkuCommittedStockBO;
import com.love.goods.dto.GoodsSkuDTO;
import com.love.goods.dto.GoodsSkuSimpleDTO;
import com.love.goods.service.GoodsSkuService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("goods/sku")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GoodsSkuController {

    private final GoodsSkuService goodsSkuService;

    @GetMapping("queryById")
    public Result<GoodsSkuDTO> queryById(IdParam idParam) {
        return Result.success(goodsSkuService.queryById(idParam.getId()));
    }

    @GetMapping("simple")
    public Result<GoodsSkuSimpleDTO> simple(IdParam idParam) {
        return Result.success(goodsSkuService.simple(idParam.getId()));
    }

    @GetMapping("modifyCommittedStock")
    public Result<Boolean> modifyCommittedStock(ModifyGoodsSkuCommittedStockBO modifyGoodsSkuCommittedStockBO) {
        return Result.success(goodsSkuService.modifyCommittedStock(modifyGoodsSkuCommittedStockBO));
    }

    @PostMapping("modifyAvailableStock")
    public Result<Boolean> modifyAvailableStock(@RequestBody ModifyGoodsSkuAvailableStockBO modifyGoodsSkuAvailableStockBO) {
        return Result.success(goodsSkuService.modifyAvailableStock(modifyGoodsSkuAvailableStockBO));
    }

    @GetMapping("queryAvailableStockByGoodsId")
    public Result<Integer> queryAvailableStockByGoodsId(IdParam idParam) {
        return Result.success(goodsSkuService.queryAvailableStockByGoodsId(idParam.getId()));
    }

    @GetMapping("queryAvailableStockByGoodsIds")
    public Result<Map<Integer, Long>> queryAvailableStockByGoodsIds(IdsParam idsParam) {
        return Result.success(goodsSkuService.queryAvailableStockByGoodsIds(idsParam.getIdList()));
    }

    @GetMapping("querySkuSpecs")
    public Result<Map<Long, String>> querySkuSpecs(IdsParam idsParam) {
        return Result.success(goodsSkuService.querySkuSpecs(idsParam.getIdList()));
    }

    @GetMapping("queryByIds")
    public Result<List<GoodsSkuDTO>> queryByIds(IdsParam idsParam) {
        return Result.success(goodsSkuService.queryByIds(idsParam.getIdList()));
    }
}
