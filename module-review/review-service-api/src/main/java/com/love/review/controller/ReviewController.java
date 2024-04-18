package com.love.review.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.review.bo.QueryLatestReviewForOrderItemBO;
import com.love.review.bo.ReviewQueryMerchantPageBO;
import com.love.review.bo.ReviewQueryUserPageBO;
import com.love.review.bo.ReviewSaveBO;
import com.love.review.dto.ReviewDTO;
import com.love.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("review")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("save")
    public Result<Long> save(@RequestBody ReviewSaveBO reviewSaveBO) {
        return Result.success(reviewService.save(reviewSaveBO));
    }

    @GetMapping("queryById")
    public Result<ReviewDTO> detail(IdParam idParam) {
        return Result.success(reviewService.queryById(idParam));
    }

    @GetMapping("deleteById")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(reviewService.deleteById(idParam));
    }

    @GetMapping("userPage")
    public Result<Pageable<ReviewDTO>> userPage(ReviewQueryUserPageBO reviewQueryUserPageBO) {
        return Result.success(reviewService.userPage(reviewQueryUserPageBO));
    }

    @GetMapping("merchantPage")
    public Result<Pageable<ReviewDTO>> merchantPage(ReviewQueryMerchantPageBO reviewQueryMerchantPageBO) {
        return Result.success(reviewService.merchantPage(reviewQueryMerchantPageBO));
    }

    @GetMapping("queryLatestForOrderItem")
    public Result<ReviewDTO> queryLatestForOrderItem(QueryLatestReviewForOrderItemBO queryLatestReviewForOrderItemBO) {
        return Result.success(reviewService.queryLatestForOrderItem(queryLatestReviewForOrderItemBO));
    }
}
