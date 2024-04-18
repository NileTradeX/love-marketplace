package com.love.goods.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.param.IdsParam;
import com.love.common.result.Result;
import com.love.goods.bo.*;
import com.love.goods.dto.*;
import com.love.goods.service.GoodsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goods")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class GoodsController {

    private final GoodsService goodsService;


    @PostMapping("savePhysical")
    public Result<Long> savePhysical(@RequestBody PhysicalGoodsSaveBO physicalGoodsSaveBO) {
        return Result.success(goodsService.savePhysical(physicalGoodsSaveBO));
    }

    @GetMapping("detail")
    public Result<GoodsDTO> detail(GoodsDetailQueryBO goodsDetailQueryBO) {
        return Result.success(goodsService.detail(goodsDetailQueryBO));
    }

    @GetMapping("simple")
    public Result<GoodsSimpleDTO> simple(IdParam idParam) {
        return Result.success(goodsService.simple(idParam));
    }

    @GetMapping("deleteById")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(goodsService.deleteById(idParam));
    }

    @GetMapping("page")
    public Result<Pageable<GoodsSimpleDTO>> page(GoodsQueryPageBO goodsQueryPageBO) {
        return Result.success(goodsService.page(goodsQueryPageBO));
    }

    @PostMapping("homepage")
    public Result<Pageable<GoodsHomepageDTO>> homePage(@RequestBody GoodsHomepageQueryBO goodsHomePageQueryBO) {
        return Result.success(goodsService.homepage(goodsHomePageQueryBO));
    }

    @GetMapping("reviewPage")
    public Result<Pageable<GoodsReviewDTO>> reviewPage(GoodsReviewPageQueryBO goodsReviewPageQueryBO) {
        return Result.success(goodsService.reviewPage(goodsReviewPageQueryBO));
    }

    @GetMapping("reviewStat")
    public Result<List<GoodsReviewStatDTO>> reviewStat() {
        return Result.success(goodsService.reviewStat());
    }

    @PostMapping("reviewApprove")
    public Result<Boolean> reviewApprove(@RequestBody GoodsReviewApproveBO approvedBO) {
        return Result.success(goodsService.reviewApprove(approvedBO));
    }

    @PostMapping("reviewReject")
    public Result<Boolean> reviewReject(@RequestBody GoodsReviewRejectBO rejectBO) {
        return Result.success(goodsService.reviewReject(rejectBO));
    }

    @GetMapping("putOff")
    public Result<Boolean> putOff(IdParam idParam) {
        return Result.success(goodsService.putOff(idParam));
    }

    @GetMapping("putOn")
    public Result<Boolean> putOn(IdParam idParam) {
        return Result.success(goodsService.putOn(idParam));
    }

    @PostMapping("duplicate")
    public Result<Long> duplicate(@RequestBody GoodsDuplicateBO goodsDuplicateBO) {
        return Result.success(goodsService.duplicate(goodsDuplicateBO));
    }

    @PostMapping("modifySalesVolume")
    public Result<Boolean> modifySalesVolume(@RequestBody UpdateGoodsSalesVolumeBO goodsSalesVolumeUpdateBO) {
        return Result.success(goodsService.modifySalesVolume(goodsSalesVolumeUpdateBO));
    }

    @PostMapping("influencerGoodsPage")
    public Result<Pageable<InfGoodsPageDTO>> influencerGoodsPage(@RequestBody InfGoodsPageQueryBO influencerGoodsPageQueryBO) {
        return Result.success(goodsService.influencerGoodsPage(influencerGoodsPageQueryBO));
    }

    @GetMapping("simpleInfluenceGoods")
    public Result<InfGoodsSimpleDTO> simpleInfluenceGoods(IdParam idParam) {
        return Result.success(goodsService.simpleInfluenceGoods(idParam));
    }

    @GetMapping("queryByIds")
    public Result<List<GoodsDTO>> queryByIds(IdsParam idsParam) {
        return Result.success(goodsService.queryByIds(idsParam));
    }

    @GetMapping("queryForHomepageByIds")
    public Result<List<GoodsHomepageDTO>> querySimpleByIds(IdsParam idsParam) {
        return Result.success(goodsService.queryForHomepageByIds(idsParam));
    }

    @PostMapping("queryRecommendGoodsByCategory")
    public Result<List<RecommendGoodsDTO>> queryRecommendGoodsByCategory(@RequestBody GoodsRecommendQueryBO goodsRecommendQueryBO) {
        return Result.success(goodsService.queryRecommendGoodsByCategory(goodsRecommendQueryBO));
    }

    @GetMapping("popularPage")
    public Result<List<GoodsHomepageDTO>> popularPage(GoodsStatusPageQueryBO goodsStatusPageQueryBO) {
        return Result.success(goodsService.popularPage(goodsStatusPageQueryBO));
    }

    @GetMapping("queryTopXSales")
    public Result<List<GoodsSimpleDTO>> queryTopXSales(@RequestParam Integer num) {
        return Result.success(goodsService.queryTopXSales(num));
    }
}
