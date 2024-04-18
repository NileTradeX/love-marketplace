package com.love.marketplace.controller;

import com.love.common.page.Pageable;
import com.love.common.result.Result;
import com.love.marketplace.manager.ReviewManager;
import com.love.marketplace.model.param.ReviewQueryPageParam;
import com.love.marketplace.model.param.ReviewSaveParam;
import com.love.marketplace.model.vo.ReviewVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("review")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "ReviewApi", description = "All Review operation")
public class ReviewController {

    private final ReviewManager reviewManager;

    @PostMapping("save")
    public Result<Long> save(@RequestBody @Validated ReviewSaveParam reviewSaveParam) {
        return Result.success(reviewManager.save(reviewSaveParam));
    }

    @GetMapping("page")
    public Result<Pageable<ReviewVO>> page(ReviewQueryPageParam reviewQueryPageParam) {
        return Result.success(reviewManager.page(reviewQueryPageParam));
    }
}
