package com.love.marketplace.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.marketplace.manager.OrderManager;
import com.love.marketplace.model.param.*;
import com.love.marketplace.model.vo.AfterSalesVO;
import com.love.marketplace.model.vo.BoltOrderTokenVO;
import com.love.marketplace.model.vo.MerchantOrderVO;
import com.love.marketplace.model.vo.OrderTracksVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Tag(name = "OrderApi", description = "All Order Api operation")
public class OrderController {

    private final OrderManager orderManager;

    @GetMapping("page")
    @Operation(operationId = "queryUserOrderPage")
    public Result<Pageable<MerchantOrderVO>> page(OrderQueryPageParam orderQueryPageParam) {
        return Result.success(orderManager.page(orderQueryPageParam));
    }

    @GetMapping("detail")
    @Operation(operationId = "queryUserOrderDetail")
    public Result<MerchantOrderVO> detail(OrderDetailParam orderDetailParam) {
        return Result.success(orderManager.detail(orderDetailParam));
    }

    @PostMapping("create/token")
    @Operation(operationId = "createBoltOrderToken")
    public Result<BoltOrderTokenVO> createToken(@RequestBody @Validated CreateOrderTokenParam createOrderTokenParam) throws Exception {
        return Result.success(orderManager.createBoltOrderToken(createOrderTokenParam));
    }

    @PostMapping("guest/create/token")
    @Operation(operationId = "createBoltOrderToken")
    public Result<BoltOrderTokenVO> guestCreateToken(@RequestBody @Validated CreateOrderTokenParam createOrderTokenParam) throws Exception {
        return Result.success(orderManager.createBoltOrderToken(createOrderTokenParam));
    }

    @GetMapping("tracks")
    @Operation(operationId = "orderTracks")
    public Result<OrderTracksVO> tracks(IdParam idParam) {
        return Result.success(orderManager.tracks(idParam));
    }

    @GetMapping("detailByEmailAndOrderNo")
    @Operation(operationId = "queryOrderDetailByEmailAndOrderNo")
    public Result<MerchantOrderVO> detailByEmailAndOrderNo(OrderGuestDetailParam orderGuestDetailParam) {
        return Result.success(orderManager.detailByEmailAndOrderNo(orderGuestDetailParam));
    }

    @GetMapping("refund/page")
    @Operation(operationId = "orderRefundPage")
    public Result<Pageable<AfterSalesVO>> afterSalesPage(AfterSalesQueryPageParam afterSalesOrderQueryPageParam) {
        return Result.success(orderManager.afterSalesPage(afterSalesOrderQueryPageParam));
    }

    @PostMapping("refund/create")
    @Operation(operationId = "orderRefundCreate")
    public Result<Boolean> refundCreate(@RequestBody @Validated RefundCreateParam refundCreateParam) {
        return Result.success(orderManager.refundCreate(refundCreateParam));
    }

    @PostMapping("refund/close")
    @Operation(operationId = "orderRefundClose")
    public Result<Boolean> refund(@RequestBody @Validated RefundCloseParam refundCloseParam) {
        return Result.success(orderManager.refundClose(refundCloseParam));
    }
}
