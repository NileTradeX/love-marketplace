package com.love.marketplace.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.goods.dto.RecommendGoodsDTO;
import com.love.marketplace.manager.GoodsManager;
import com.love.marketplace.model.param.GoodsHomepageQueryParam;
import com.love.marketplace.model.param.GoodsRecommendQueryParam;
import com.love.marketplace.model.vo.GoodsHomepageVO;
import com.love.marketplace.model.vo.GoodsVO;
import com.love.marketplace.model.vo.HomepageV2VO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("goods")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "GoodsApi", description = "All Goods operation")
public class GoodsController {

    private final GoodsManager goodsManager;

    @GetMapping("trendingNow")
    @Operation(operationId = "trendingNow")
    public Result<Pageable<GoodsHomepageVO>> trendingNow() {
        return Result.success(goodsManager.trendingNow());
    }

    @PostMapping("page")
    @Operation(operationId = "queryGoods")
    public Result<Pageable<GoodsHomepageVO>> page(@RequestBody @Validated GoodsHomepageQueryParam goodsQueryHomePageParam) {
        return Result.success(goodsManager.page(goodsQueryHomePageParam));
    }

    @GetMapping("page/v2")
    @Operation(operationId = "queryGoods")
    public Result<List<HomepageV2VO>> pageV2() {
        return Result.success(goodsManager.pageV2());
    }

    @GetMapping("detail")
    @Operation(operationId = "goodsDetail")
    public Result<GoodsVO> detail(IdParam param) {
        return Result.success(goodsManager.detail(param));
    }

    @PostMapping("queryRecommendGoodsByCategory")
    @Operation(operationId = "queryRecommendGoodsByCategory")
    public Result<List<RecommendGoodsDTO>> queryRecommendGoodsByCategory(@RequestBody @Validated GoodsRecommendQueryParam goodsRecommendQueryParam) {
        return Result.success(goodsManager.queryRecommendGoodsByCategory(goodsRecommendQueryParam));
    }

    @GetMapping("updateIndex")
    public Result<Boolean> updateIndex(@RequestParam("goodsId") String goodsId) {
        return Result.success(goodsManager.updateIndex(goodsId));
    }

    @GetMapping("popularPage")
    @Operation(operationId = "queryPopularGoodsPage")
    public Result<List<GoodsHomepageVO>> popularPage() {
        return Result.success(goodsManager.popularPage());
    }
}
