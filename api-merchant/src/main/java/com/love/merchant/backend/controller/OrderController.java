package com.love.merchant.backend.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.merchant.backend.manager.OrderManager;
import com.love.merchant.backend.model.param.*;
import com.love.merchant.backend.model.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "OrderApi")
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

    @PostMapping("saveTracking")
    @Operation(operationId = "orderSaveTracking")
    public Result<Boolean> saveTracking(@RequestBody @Validated OrderTrackingInfoParam orderTrackingInfoParam) {
        return Result.success(orderManager.saveTracking(orderTrackingInfoParam));
    }

    @PostMapping("updateTracking")
    @Operation(operationId = "orderUpdateTracking")
    public Result<Boolean> updateTracking(@RequestBody @Validated OrderTrackingInfoParam orderTrackingInfoParam) {
        return Result.success(orderManager.updateTracking(orderTrackingInfoParam));
    }

    @GetMapping("queryTracking")
    @Operation(operationId = "orderQueryTracking")
    public Result<OrderTrackInfoVO> queryTracking(IdParam idParam) {
        return Result.success(orderManager.queryTracking(idParam));
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

    @PostMapping("refund/agree")
    @Operation(operationId = "agreeOrderRefund")
    public Result<Boolean> agreeOrderRefund(@RequestBody @Validated OrderRefundAgreeParam orderRefundAgreeParam) {
        return Result.success(orderManager.agreeOrderRefund(orderRefundAgreeParam));
    }

    @PostMapping("refund/decline")
    @Operation(operationId = "declineOrderRefund")
    public Result<Boolean> declineOrderRefund(@RequestBody @Validated OrderRefundDeclineParam orderRefundDeclineParam) {
        return Result.success(orderManager.declineOrderRefund(orderRefundDeclineParam));
    }
}
