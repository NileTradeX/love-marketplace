package com.love.order.controller;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.common.result.Result;
import com.love.order.bo.*;
import com.love.order.dto.*;
import com.love.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderController {

    private final OrderService orderService;

    @GetMapping("page")
    public Result<Pageable<MerchantOrderDTO>> page(OrderQueryPageBO orderQueryPageBO) {
        return Result.success(orderService.page(orderQueryPageBO));
    }

    @GetMapping("queryById")
    public Result<OrderDTO> queryById(IdParam idParam) {
        return Result.success(orderService.queryById(idParam));
    }

    @GetMapping("queryByOrderNo")
    public Result<OrderDTO> queryByOrderNo(QuerySimpleOrderBO queryByOrderNoBO) {
        return Result.success(orderService.queryByOrderNo(queryByOrderNoBO));
    }

    @GetMapping("stat/simple")
    public Result<OrderSimpleStatDTO> simpleStat(OrderSimpleStatBO orderSimpleStatBO) {
        return Result.success(orderService.simpleStat(orderSimpleStatBO));
    }

    @GetMapping("stat/user")
    public Result<UserOrderStatDTO> userStat(UserOrderStatBO userOrderStatBO) {
        return Result.success(orderService.userStat(userOrderStatBO));
    }

    @GetMapping("user/page")
    public Result<Pageable<MerchantOrderDTO>> userPage(UserQueryOrderPageBO userQueryOrderPageBO) {
        return Result.success(orderService.userPage(userQueryOrderPageBO));
    }

    @GetMapping("countByUserId")
    public Result<Long> countByUserId(IdParam idParam) {
        return Result.success(orderService.countByUserId(idParam));
    }

    @GetMapping("merchantOrderDetail")
    public Result<OrderDTO> merchantOrderDetail(OrderQueryDetailBO orderDetailBO) {
        return Result.success(orderService.merchantOrderDetail(orderDetailBO));
    }

    @GetMapping("simple")
    public Result<OrderSimpleDTO> simple(QuerySimpleOrderBO querySimpleOrderBO) {
        return Result.success(orderService.simple(querySimpleOrderBO));
    }

    @PostMapping("saveTracking")
    public Result<Boolean> saveTracking(@RequestBody OrderTrackingInfoBO orderTrackingInfoBO) {
        return Result.success(orderService.saveTracking(orderTrackingInfoBO));
    }

    @PostMapping("updateTracking")
    public Result<Boolean> updateTracking(@RequestBody OrderTrackingInfoBO orderTrackingInfoBO) {
        return Result.success(orderService.updateTracking(orderTrackingInfoBO));
    }

    @GetMapping("queryTracking")
    public Result<OrderTrackInfoDTO> queryTracking(IdParam idParam) {
        return Result.success(orderService.queryTracking(idParam));
    }

    @PostMapping("updateByOrderNo")
    public Result<Boolean> updateByOrderNo(@RequestBody OrderUpdateByOrderNoBO orderUpdateByOrderNoBO) {
        return Result.success(orderService.updateByOrderNo(orderUpdateByOrderNoBO));
    }

    @PostMapping("updateByMerOrderNo")
    public Result<Boolean> updateByMerOrderNo(@RequestBody OrderUpdateByMerOrderNoBO orderUpdateByMerOrderNoBO) {
        return Result.success(orderService.updateByMerOrderNo(orderUpdateByMerOrderNoBO));
    }

    @GetMapping("queryByOrderNoAndEmail")
    public Result<OrderDTO> queryByOrderNoAndEmail(QueryByEmailAndOrderNoBO queryByEmailAndOrderNoBO) {
        return Result.success(orderService.queryByOrderNoAndEmail(queryByEmailAndOrderNoBO));
    }

    @PostMapping("create")
    public Result<MultiItemOrderDTO> create(@RequestBody MultiItemOrderSaveBO multiItemOrderSaveBO) {
        return Result.success(orderService.create(multiItemOrderSaveBO));
    }

    @GetMapping("queryOrderList")
    public Result<List<OrderSimpleDTO>> queryOrderList(QueryOrderNoListBO queryOrderNoListBO) {
        return Result.success(orderService.queryOrderList(queryOrderNoListBO));
    }

    @GetMapping("checkCompleted")
    public Result<Boolean> checkOrderCompleted(IdParam idParam) {
        return Result.success(orderService.checkOrderCompleted(idParam));
    }

    @GetMapping("queryMerchantOrderList")
    public Result<List<MerchantOrderSimpleDTO>> queryMerchantOrderList(QueryMerchantOrderNoListBO queryMerchantOrderNoListBO) {
        return Result.success(orderService.queryMerchantOrderList(queryMerchantOrderNoListBO));
    }

    @PostMapping("migrateGuestOrder")
    public Result<Boolean> migrateGuestOrder(@RequestBody MigrateGuestOrderBO migrateGuestOrderBO) {
        return Result.success(orderService.migrateGuestOrder(migrateGuestOrderBO));
    }

    @GetMapping("queryByOrderItemId")
    public Result<OrderItemDTO> queryByOrderItemId(IdParam idParam) {
        return Result.success(orderService.queryByOrderItemId(idParam));
    }

    @GetMapping("checkPromoEligibility")
    public Result<Boolean> checkPromoEligibility(PromoEligibilityParam param) {
        return Result.success(orderService.checkPromoEligibility(param));
    }
    @PostMapping("completeOrderByTrack")
    public Result<Boolean> completeOrderByTrack(@RequestBody CompleteOrderByTrackBO completeOrderByTrackBO) {
        return Result.success(orderService.completeOrderByTrack(completeOrderByTrackBO));
    }

    @GetMapping("queryByTrack")
    public Result<List<OrderItemDTO>> queryByTrack(CompleteOrderByTrackBO completeOrderByTrackBO) {
        return Result.success(orderService.queryByTrack(completeOrderByTrackBO));
    }
}
