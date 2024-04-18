package com.love.review.controller;

import com.love.common.page.Pageable;
import com.love.common.result.Result;
import com.love.review.bo.ReviewApproveBO;
import com.love.review.bo.ReviewQueryAuditPageBO;
import com.love.review.bo.ReviewRejectBO;
import com.love.review.dto.ReviewDTO;
import com.love.review.dto.ReviewStatDTO;
import com.love.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("review/audit")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReviewAuditController {

    private final ReviewService reviewService;

    @PostMapping("approve")
    public Result<Boolean> approve(@RequestBody ReviewApproveBO reviewApproveBO) {
        return Result.success(reviewService.approve(reviewApproveBO));
    }

    @PostMapping("reject")
    public Result<Boolean> reject(@RequestBody ReviewRejectBO reviewRejectBO) {
        return Result.success(reviewService.reject(reviewRejectBO));
    }

    @GetMapping("page")
    public Result<Pageable<ReviewDTO>> page(ReviewQueryAuditPageBO reviewQueryAuditPageBO) {
        return Result.success(reviewService.auditPage(reviewQueryAuditPageBO));
    }

    @GetMapping("stat")
    public Result<List<ReviewStatDTO>> stat() {
        return Result.success(reviewService.stat());
    }
}
