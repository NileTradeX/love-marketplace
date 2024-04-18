package com.love.order.client;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.order.bo.*;
import com.love.order.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "order-service-api", contextId = "orderFeignClient", path = "order")
public interface OrderFeignClient {

    @GetMapping("page")
    Pageable<MerchantOrderDTO> page(@SpringQueryMap OrderQueryPageBO orderQueryPageBO);

    @GetMapping("queryById")
    OrderDTO queryById(@SpringQueryMap IdParam idParam);

    @GetMapping("queryByOrderNo")
    OrderDTO queryByOrderNo(@SpringQueryMap QuerySimpleOrderBO queryByOrderNoBO);

    @GetMapping("stat/simple")
    OrderSimpleStatDTO simpleStat(@SpringQueryMap OrderSimpleStatBO orderSimpleStatBO);

    @GetMapping("stat/user")
    UserOrderStatDTO userStat(@SpringQueryMap UserOrderStatBO userOrderStatBO);

    @GetMapping("user/page")
    Pageable<MerchantOrderDTO> userPage(@SpringQueryMap UserQueryOrderPageBO userQueryOrderPageBO);

    @GetMapping("countByUserId")
    Long countByUserId(@SpringQueryMap IdParam idParam);

    @GetMapping("merchantOrderDetail")
    OrderDTO merchantOrderDetail(@SpringQueryMap OrderQueryDetailBO orderDetailBO);

    @GetMapping("simple")
    OrderSimpleDTO simple(@SpringQueryMap QuerySimpleOrderBO querySimpleOrderBO);

    @PostMapping("saveTracking")
    Boolean saveTracking(OrderTrackingInfoBO orderTrackingInfoBO);

    @PostMapping("updateTracking")
    Boolean updateTracking(OrderTrackingInfoBO orderTrackingInfoBO);

    @GetMapping("queryTracking")
    OrderTrackInfoDTO queryTracking(@SpringQueryMap IdParam idParam);

    @PostMapping("updateByOrderNo")
    Boolean updateByOrderNo(OrderUpdateByOrderNoBO orderUpdateByOrderNoBO);

    @PostMapping("updateByMerOrderNo")
    Boolean updateByMerOrderNo(OrderUpdateByMerOrderNoBO orderUpdateByMerOrderNoBO);

    @GetMapping("queryByOrderNoAndEmail")
    OrderDTO queryByOrderNoAndEmail(@SpringQueryMap QueryByEmailAndOrderNoBO queryByEmailAndOrderNoBO);

    @PostMapping("create")
    MultiItemOrderDTO create(MultiItemOrderSaveBO multiItemOrderSaveBO);

    @GetMapping("queryOrderList")
    List<OrderSimpleDTO> queryOrderList(@SpringQueryMap QueryOrderNoListBO queryOrderNoListBO);

    @GetMapping("checkCompleted")
    Boolean checkCompleted(@SpringQueryMap IdParam idParam);

    @GetMapping("queryMerchantOrderList")
    List<MerchantOrderSimpleDTO> queryMerchantOrderList(@SpringQueryMap QueryMerchantOrderNoListBO queryMerchantOrderNoListBO);

    @PostMapping("migrateGuestOrder")
    Boolean migrateGuestOrder(MigrateGuestOrderBO migrateGuestOrderBO);

    @GetMapping("queryByOrderItemId")
    OrderItemDTO queryByOrderItemId(@SpringQueryMap IdParam idParam);

    @GetMapping("checkPromoEligibility")
    Boolean checkPromoEligibility(@SpringQueryMap PromoEligibilityParam param);

    @PostMapping("completeOrderByTrack")
    Boolean completeOrderByTrack(@RequestBody CompleteOrderByTrackBO completeOrderByTrackBO);

    @GetMapping("queryByTrack")
    List<OrderItemDTO> queryByTrack(@SpringQueryMap CompleteOrderByTrackBO completeOrderByTrackBO);
}
