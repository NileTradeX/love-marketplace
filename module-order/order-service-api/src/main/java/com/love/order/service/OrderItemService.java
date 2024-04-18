package com.love.order.service;

import com.love.order.bo.OrderTrackingInfoBO;
import com.love.order.entity.OrderItem;

import java.util.List;
import java.util.Set;

public interface OrderItemService {
    boolean saveBatch(List<OrderItem> orderItems);

    List<OrderItem> queryByMerchantOrderId(Long merchantOrderId);

    boolean updateStatusByOrderId(Long orderId, Integer status, List<Long> skuIdList);

    Set<Long> queryMerchantOrderId(List<Long> skuIdList);

    Boolean saveTracking(OrderTrackingInfoBO orderTrackingInfoBO, List<Long> excludeSkuIdList);

    Boolean updateTracking(OrderTrackingInfoBO orderTrackingInfoBO, List<Long> excludeSkuIdList);

    OrderItem queryTracking(Long orderId);

    Boolean checkOrderCompleted(Long orderItemId);

    Boolean updateStatusByMerOrderId(Long merOrderId, Integer status, List<Long> skuIdList);

    OrderItem queryById(Long id);

    List<OrderItem> queryByOrderId(Long orderId);

    List<OrderItem> queryByTrack(String trackNo,String carriers);
}
