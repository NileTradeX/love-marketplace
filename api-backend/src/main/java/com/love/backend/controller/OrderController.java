package com.love.backend.controller;

import com.love.backend.manager.OrderManager;
import com.love.backend.model.param.AfterSalesOrderQueryPageParam;
import com.love.backend.model.param.AfterSalesQueryParam;
import com.love.backend.model.param.OrderQueryPageParam;
import com.love.backend.model.param.OrderSimpleStatParam;
import com.love.backend.model.vo.*;
import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "OrderApi", description = "all Order manage operation")
public class OrderController {

    private final OrderManager orderManager;

    @GetMapping("page")
    @Operation(operationId = "queryOrderPage")
    public Result<Pageable<MerchantOrderVO>> page(OrderQueryPageParam orderQueryPageParam) {
        return Result.success(orderManager.page(orderQueryPageParam));
    }

    @GetMapping("detail")
    @Operation(operationId = "queryOrderDetail")
    public Result<MerchantOrderVO> detail(IdParam idParam) {
        return Result.success(orderManager.detail(idParam));
    }

    @GetMapping("simple/stat")
    @Operation(operationId = "orderSimpleStat")
    public Result<OrderSimpleStatVO> simpleStat(OrderSimpleStatParam orderSimpleStatParam) {
        return Result.success(orderManager.simpleStat(orderSimpleStatParam));
    }

    @GetMapping("tracks")
    @Operation(operationId = "orderTracks")
    public Result<OrderTracksVO> tracks(IdParam idParam) {
        return Result.success(orderManager.tracks(idParam));
    }

    @GetMapping("afterSales/page")
    @Operation(operationId = "afterSalesPage")
    public Result<Pageable<AfterSalesRecordVO>> afterSalesPage(AfterSalesOrderQueryPageParam afterSalesOrderQueryPageParam) {
        return Result.success(orderManager.afterSalesPage(afterSalesOrderQueryPageParam));
    }

    @GetMapping("afterSales/detail")
    @Operation(operationId = "afterSalesDetail")
    public Result<AfterSalesDetailVO> afterSalesDetail(AfterSalesQueryParam afterSalesOrderQueryPageParam) {
        return Result.success(orderManager.afterSalesDetail(afterSalesOrderQueryPageParam));
    }
}
