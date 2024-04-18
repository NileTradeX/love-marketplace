package com.love.merchant.controller;

import com.love.common.page.Pageable;
import com.love.common.result.Result;
import com.love.merchant.bo.CommissionRateQueryPageBO;
import com.love.merchant.bo.CommissionRateSaveBO;
import com.love.merchant.dto.CommissionRateDTO;
import com.love.merchant.service.CommissionRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("mer/fee/rate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CommissionRateController {

    private final CommissionRateService commissionRateService;

    @PostMapping("save")
    public Result<Boolean> save(@RequestBody CommissionRateSaveBO commissionRateSaveBO) {
        return Result.success(commissionRateService.save(commissionRateSaveBO));
    }

    @GetMapping("queryCurrent")
    public Result<BigDecimal> queryCurrent(@RequestParam("merchantId") Long merchantId) {
        return Result.success(commissionRateService.queryCurrent(merchantId));
    }

    @GetMapping("page")
    public Result<Pageable<CommissionRateDTO>> page(CommissionRateQueryPageBO commissionFeeRateQueryPageBO) {
        return Result.success(commissionRateService.page(commissionFeeRateQueryPageBO));
    }
}
