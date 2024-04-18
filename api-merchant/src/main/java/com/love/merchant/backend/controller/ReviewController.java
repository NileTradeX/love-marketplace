package com.love.merchant.backend.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.merchant.backend.manager.ReviewManager;
import com.love.merchant.backend.model.param.ReviewQueryPageParam;
import com.love.merchant.backend.model.vo.ReviewVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("review")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "ReviewApi", description = "All Review operation")
public class ReviewController {

    private final ReviewManager reviewManager;

    @GetMapping("detail")
    public Result<ReviewVO> detail(IdParam idParam) {
        return Result.success(reviewManager.detail(idParam));
    }

    @GetMapping("deleteById")
    public Result<Boolean> deleteById(IdParam idParam) {
        return Result.success(reviewManager.deleteById(idParam));
    }

    @GetMapping("page")
    public Result<Pageable<ReviewVO>> page(ReviewQueryPageParam reviewQueryPageParam) {
        return Result.success(reviewManager.page(reviewQueryPageParam));
    }
}
