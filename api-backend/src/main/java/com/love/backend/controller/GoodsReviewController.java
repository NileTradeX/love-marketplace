package com.love.backend.controller;

import com.love.backend.manager.GoodsManager;
import com.love.backend.model.param.GoodsReviewApproveParam;
import com.love.backend.model.param.GoodsReviewPageParam;
import com.love.backend.model.param.GoodsReviewRejectParam;
import com.love.backend.model.vo.GoodsReviewStatVO;
import com.love.backend.model.vo.GoodsReviewVO;
import com.love.common.page.Pageable;
import com.love.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("goods/review")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "GoodsReviewApi", description = "All Goods Review operation")
public class GoodsReviewController {

    private final GoodsManager goodsManager;

    @PostMapping("approve")
    @Operation(operationId = "approveGoodsReview")
    public Result<Boolean> approve(@RequestBody @Validated GoodsReviewApproveParam goodsReviewApproveParam) {
        return Result.success(goodsManager.reviewApprove(goodsReviewApproveParam));
    }

    @PostMapping("reject")
    @Operation(operationId = "rejectGoodsReview")
    public Result<Boolean> reject(@RequestBody @Validated GoodsReviewRejectParam goodsReviewRejectParam) {
        return Result.success(goodsManager.reviewReject(goodsReviewRejectParam));
    }

    @GetMapping("page")
    @Operation(operationId = "queryGoodsReviewPage")
    public Result<Pageable<GoodsReviewVO>> page(GoodsReviewPageParam goodsReviewPageParam) {
        return Result.success(goodsManager.reviewPage(goodsReviewPageParam));
    }

    @GetMapping("stat")
    @Operation(operationId = "queryReviewStat")
    public Result<List<GoodsReviewStatVO>> reviewStat() {
        return Result.success(goodsManager.reviewStat());
    }

}
