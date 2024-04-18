package com.love.influencer.backend.controller;

import com.love.common.page.Pageable;
import com.love.common.param.ByUserIdParam;
import com.love.common.result.Result;
import com.love.influencer.backend.manager.InfluencerGoodsManager;
import com.love.influencer.backend.model.param.InfGoodsBatchSaveParam;
import com.love.influencer.backend.model.param.InfGoodsPageQueryParam;
import com.love.influencer.backend.model.param.InfGoodsQueryPageParam;
import com.love.influencer.backend.model.param.InfGoodsUpdateStatusParam;
import com.love.influencer.backend.model.vo.InfGoodsPageVO;
import com.love.influencer.backend.model.vo.InfGoodsStatusCountVO;
import com.love.influencer.backend.model.vo.InfGoodsVO;
import com.love.influencer.dto.InfGoodsDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("influencer/goods")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "InfluencerGoodsApi", description = "all Influencer Goods manage operation")
public class InfluencerGoodsController {

    private final InfluencerGoodsManager influencerGoodsManager;

    @PostMapping("batchSave")
    public Result<Boolean> batchSave(@RequestBody @Validated InfGoodsBatchSaveParam infGoodsBatchSaveParam) {
        return Result.success(influencerGoodsManager.batchSave(infGoodsBatchSaveParam));
    }

    @GetMapping("page")
    public Result<Pageable<InfGoodsVO>> page(InfGoodsQueryPageParam infGoodsQueryPageParam) {
        return Result.success(influencerGoodsManager.page(infGoodsQueryPageParam));
    }

    @PostMapping("updateStatus")
    public Result<Boolean> updateStatus(@RequestBody @Validated InfGoodsUpdateStatusParam updateInfluencerGoodsStatusParam) {
        return Result.success(influencerGoodsManager.updateStatus(updateInfluencerGoodsStatusParam));
    }

    @PostMapping("goodsPage")
    public Result<Pageable<InfGoodsPageVO>> influencerGoodsPage(@RequestBody @Validated InfGoodsPageQueryParam influencerGoodsQueryParam) {
        return Result.success(influencerGoodsManager.influencerGoodsPage(influencerGoodsQueryParam));
    }

    @GetMapping("statusCount")
    public Result<InfGoodsStatusCountVO> statusCount(ByUserIdParam byUserIdParam) {
        return Result.success(influencerGoodsManager.statusCount(byUserIdParam));
    }
}
