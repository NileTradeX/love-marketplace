package com.love.influencer.controller;

import com.love.common.page.Pageable;
import com.love.common.result.Result;
import com.love.influencer.bo.*;
import com.love.influencer.dto.InfGoodsDTO;
import com.love.influencer.dto.InfGoodsIdDTO;
import com.love.influencer.dto.InfGoodsSimpleDTO;
import com.love.influencer.dto.InfGoodsStatusCountDTO;
import com.love.influencer.service.InfGoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("influencer/goods")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfGoodsController {

    private final InfGoodsService infGoodsService;

    @PostMapping("batchSave")
    public Result<Boolean> batchSave(@RequestBody InfGoodsBatchSaveBO infGoodsBatchSaveBO) {
        return Result.success(infGoodsService.batchSave(infGoodsBatchSaveBO));
    }

    @GetMapping("page")
    public Result<Pageable<InfGoodsDTO>> page(InfGoodsQueryPageBO infGoodsQueryPageBO) {
        return Result.success(infGoodsService.page(infGoodsQueryPageBO));
    }

    @PostMapping("updateStatus")
    public Result<Boolean> updateStatus(@RequestBody InfGoodsUpdateStatusBO infGoodsUpdateStatusBO) {
        return Result.success(infGoodsService.updateStatus(infGoodsUpdateStatusBO));
    }

    @GetMapping("statusCount")
    public Result<InfGoodsStatusCountDTO> statusCount(InfGoodsStatusCountBO infGoodsStatusCountQueryBO) {
        return Result.success(infGoodsService.statusCount(infGoodsStatusCountQueryBO));
    }

    @GetMapping("queryByGoodsIdAndInfluencerId")
    public Result<InfGoodsSimpleDTO> queryByGoodsIdAndInfluencerId(InfGoodsCommissionQueryBO infGoodsCommissionQueryBO) {
        return Result.success(infGoodsService.queryByGoodsIdAndInfluencerId(infGoodsCommissionQueryBO));
    }

    @GetMapping("queryGoodsIdByInfluencerId")
    public Result<List<InfGoodsIdDTO>> queryGoodsIdByInfluencerId(InfGoodsQueryListBO infGoodsQueryListBO) {
        return Result.success(infGoodsService.queryGoodsIdByInfluencerId(infGoodsQueryListBO));
    }

    @PostMapping("updateById")
    public Result<Boolean> updateGoodsStatus(@RequestBody InfGoodsUpdateByIdBO infGoodsUpdateByIdBO) {
        return Result.success(infGoodsService.updateGoodsById(infGoodsUpdateByIdBO));
    }

    @PostMapping("updateGoodsAvailableStock")
    public Result<Boolean> updateGoodsAvailableStock(@RequestBody InfGoodsStockUpdateBO infGoodsStockUpdateBO) {
        return Result.success(infGoodsService.updateGoodsAvailableStock(infGoodsStockUpdateBO));
    }

    @PostMapping("modifySalesVolume")
    public Result<Boolean> modifySalesVolume(@RequestBody InfGoodsSalesVolumeUpdateBO infGoodsSalesVolumeUpdateBO) {
        return Result.success(infGoodsService.modifySalesVolume(infGoodsSalesVolumeUpdateBO));
    }
}
