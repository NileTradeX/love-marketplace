package com.love.order.service;

import com.love.common.page.Pageable;
import com.love.common.param.IdParam;
import com.love.order.bo.*;
import com.love.order.dto.*;
import com.love.order.entity.Order;
import com.love.order.entity.OrderItem;

import java.util.List;

public interface OrderService {

    Pageable<MerchantOrderDTO> page(OrderQueryPageBO orderQueryPageBO);

    OrderDTO queryById(IdParam idParam);

    OrderDTO queryByOrderNo(QuerySimpleOrderBO queryByOrderNoBO);

    OrderSimpleStatDTO simpleStat(OrderSimpleStatBO orderSimpleStatBO);

    UserOrderStatDTO userStat(UserOrderStatBO userOrderStatBO);

    Pageable<MerchantOrderDTO> userPage(UserQueryOrderPageBO userQueryOrderPageBO);

    Long countByUserId(IdParam idParam);

    Boolean saveTracking(OrderTrackingInfoBO orderTrackingInfoBO);

    Boolean updateTracking(OrderTrackingInfoBO orderTrackingInfoBO);

    OrderTrackInfoDTO queryTracking(IdParam orderId);

    OrderDTO merchantOrderDetail(OrderQueryDetailBO orderQueryDetailBO);

    OrderSimpleDTO simple(QuerySimpleOrderBO idParam);

    Boolean updateByOrderNo(OrderUpdateByOrderNoBO orderUpdateByOrderNoBO);

    Boolean updateByMerOrderNo(OrderUpdateByMerOrderNoBO orderUpdateByMerOrderNoBO);

    Boolean updateOrderItemByMerOrderNo(OrderItemUpdateByMerOrderNoBO orderItemUpdateByMerOrderNoBO);

    OrderDTO queryByOrderNoAndEmail(QueryByEmailAndOrderNoBO queryByOrderNoBO);

    MultiItemOrderDTO create(MultiItemOrderSaveBO multiItemOrderSaveBO);

    List<OrderSimpleDTO> queryOrderList(QueryOrderNoListBO queryOrderNoListBO);

    Boolean checkOrderCompleted(IdParam idParam);

    List<MerchantOrderSimpleDTO> queryMerchantOrderList(QueryMerchantOrderNoListBO queryMerchantOrderNoListBO);

    Boolean migrateGuestOrder(MigrateGuestOrderBO migrateGuestOrderBO);

    OrderItemDTO queryByOrderItemId(IdParam idParam);

    List<Order> queryByStatus(int status);

    Boolean checkPromoEligibility(PromoEligibilityParam param);

    Boolean completeOrderByTrack(CompleteOrderByTrackBO completeOrderByTrackBO);

    List<OrderItemDTO> queryByTrack(CompleteOrderByTrackBO completeOrderByTrackBO);
}
