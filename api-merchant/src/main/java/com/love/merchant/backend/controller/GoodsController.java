package com.love.merchant.backend.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.merchant.backend.manager.GoodsManager;
import com.love.merchant.backend.model.param.GoodsDuplicateParam;
import com.love.merchant.backend.model.param.GoodsQueryPageParam;
import com.love.merchant.backend.model.param.ModifyGoodsSkuAvailableStockParam;
import com.love.merchant.backend.model.param.PhysicalGoodsSaveParam;
import com.love.merchant.backend.model.vo.GoodsSimpleVO;
import com.love.merchant.backend.model.vo.GoodsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("goods")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "GoodsApi", description = "all Goods management operation")
public class GoodsController {

    private final GoodsManager goodsManager;

    @PostMapping("savePhysical")
    @Operation(operationId = "savePhysicalGoods")
    public Result<Long> savePhysical(@RequestBody @Validated PhysicalGoodsSaveParam physicalGoodsSaveParam) {
        return Result.success(goodsManager.savePhysical(physicalGoodsSaveParam));
    }

    @GetMapping("detail")
    @Operation(operationId = "goodsDetail")
    public Result<GoodsVO> detail(IdParam idParam) {
        return Result.success(goodsManager.detail(idParam));
    }

    @GetMapping("deleteById")
    @Operation(operationId = "deleteGoods")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(goodsManager.deleteById(idParam));
    }

    @GetMapping("page")
    @Operation(operationId = "queryGoodsPage")
    public Result<Pageable<GoodsSimpleVO>> page(@Validated GoodsQueryPageParam goodsQueryPageParam) {
        return Result.success(goodsManager.page(goodsQueryPageParam));
    }

    @GetMapping("putOff")
    @Operation(operationId = "putOffGoods")
    public Result<Boolean> putOff(IdParam idParam) {
        return Result.success(goodsManager.putOff(idParam));
    }

    @GetMapping("putOn")
    @Operation(operationId = "putOnGoods")
    public Result<Boolean> putOn(IdParam idParam) {
        return Result.success(goodsManager.putOn(idParam));
    }

    @PostMapping("duplicate")
    @Operation(operationId = "duplicateGoods")
    public Result<Long> duplicate(@RequestBody @Validated GoodsDuplicateParam duplicateParam) {
        return Result.success(goodsManager.duplicate(duplicateParam));
    }

    @PostMapping("modifyAvailableStock")
    @Operation(operationId = "modifyAvailableStock")
    public Result<Boolean> modifyAvailableStock(@RequestBody @Validated ModifyGoodsSkuAvailableStockParam modifyGoodsSkuAvailableStockParam) {
        return Result.success(goodsManager.modifyGoodsSkuAvailableStock(modifyGoodsSkuAvailableStockParam));
    }

}
