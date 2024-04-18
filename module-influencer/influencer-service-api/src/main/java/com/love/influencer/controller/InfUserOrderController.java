package com.love.influencer.controller;

import com.love.common.page.Pageable;
import com.love.common.result.Result;
import com.love.influencer.bo.InfUserOrderQueryPageBO;
import com.love.influencer.bo.InfUserOrderRefundBO;
import com.love.influencer.bo.InfUserOrderSaveBO;
import com.love.influencer.dto.InfUserOrderDTO;
import com.love.influencer.service.InfUserOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("influencer/order")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class InfUserOrderController {

    private final InfUserOrderService infUserOrderService;

    @PostMapping("save")
    public Result<Boolean> save(@RequestBody InfUserOrderSaveBO infUserOrderSaveBO) {
        return Result.success(infUserOrderService.save(infUserOrderSaveBO));
    }

    @GetMapping("page")
    public Result<Pageable<InfUserOrderDTO>> page(InfUserOrderQueryPageBO infUserOrderQueryPageBO) {
        return Result.success(infUserOrderService.page(infUserOrderQueryPageBO));
    }

    @PostMapping("refund")
    public Result<Boolean> refund(@RequestBody InfUserOrderRefundBO infUserOrderRefundBO) {
        return Result.success(infUserOrderService.refund(infUserOrderRefundBO));
    }

}
