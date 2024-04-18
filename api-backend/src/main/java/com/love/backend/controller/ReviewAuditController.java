package com.love.backend.controller;

import com.love.backend.manager.ReviewAuditManager;
import com.love.backend.model.param.ReviewApproveParam;
import com.love.backend.model.param.ReviewAuditQueryPageParam;
import com.love.backend.model.param.ReviewRejectParam;
import com.love.backend.model.vo.ReviewStatVO;
import com.love.backend.model.vo.ReviewVO;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("review/audit")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "ReviewAuditApi", description = "All review audit operation")
public class ReviewAuditController {

    private final ReviewAuditManager reviewAuditManager;

    @PostMapping("approve")
    public Result<Boolean> approve(@RequestBody @Validated ReviewApproveParam reviewApproveParam) {
        return Result.success(reviewAuditManager.approve(reviewApproveParam));
    }

    @PostMapping("reject")
    public Result<Boolean> reject(@RequestBody @Validated ReviewRejectParam reviewRejectParam) {
        return Result.success(reviewAuditManager.reject(reviewRejectParam));
    }

    @GetMapping("page")
    public Result<Pageable<ReviewVO>> page(ReviewAuditQueryPageParam reviewAuditQueryPageParam) {
        return Result.success(reviewAuditManager.page(reviewAuditQueryPageParam));
    }

    @GetMapping("stat")
    @Operation(operationId = "queryReviewStat")
    public Result<List<ReviewStatVO>> reviewStat() {
        return Result.success(reviewAuditManager.stat());
    }

    @GetMapping("detail")
    @Operation(operationId = "queryReviewDetail")
    public Result<ReviewVO> detail(IdParam idParam) {
        return Result.success(reviewAuditManager.detail(idParam));
    }
}
