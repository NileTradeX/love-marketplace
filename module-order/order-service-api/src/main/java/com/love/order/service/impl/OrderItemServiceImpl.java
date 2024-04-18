package com.love.order.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.love.order.bo.OrderTrackingInfoBO;
import com.love.order.entity.AfterSalesRecord;
import com.love.order.entity.AfterSalesSku;
import com.love.order.entity.OrderItem;
import com.love.order.enums.AfterSaleStatus;
import com.love.order.enums.OrderStatus;
import com.love.order.mapper.OrderItemMapper;
import com.love.order.service.AfterSalesRecordService;
import com.love.order.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    private final TaskExecutor taskExecutor;

    @Autowired
    private AfterSalesRecordService afterSalesRecordService;

    @Override
    public boolean saveBatch(List<OrderItem> orderItems) {
        return super.saveBatch(orderItems);
    }

    @Override
    public List<OrderItem> queryByMerchantOrderId(Long merchantOrderId) {
        return this.lambdaQuery().eq(OrderItem::getMerchantOrderId, merchantOrderId).list();
    }

    @Override
    public boolean updateStatusByOrderId(Long orderId, Integer status, List<Long> skuIdList) {
        return this.lambdaUpdate().eq(OrderItem::getOrderId, orderId).in(CollUtil.isNotEmpty(skuIdList), OrderItem::getSkuId, skuIdList).set(OrderItem::getUpdateTime, LocalDateTime.now()).set(OrderItem::getStatus, status).update();
    }

    @Override
    public Set<Long> queryMerchantOrderId(List<Long> skuIdList) {
        return this.lambdaQuery().select(OrderItem::getMerchantOrderId).in(OrderItem::getSkuId, skuIdList).list().stream().map(OrderItem::getMerchantOrderId).collect(Collectors.toSet());
    }

    @Override
    public Boolean saveTracking(OrderTrackingInfoBO orderTrackingInfoBO, List<Long> excludeSkuIdList) {
        return this.lambdaUpdate()
                .set(OrderItem::getCarriers, orderTrackingInfoBO.getCarriers())
                .set(OrderItem::getTrackingNo, orderTrackingInfoBO.getTrackingNo())
                .set(OrderItem::getDeliveryTime, LocalDateTime.now())
                .set(OrderItem::getStatus, OrderStatus.PENDING_RECEIPT.getStatus())
                .set(OrderItem::getUpdateTime, LocalDateTime.now())
                .eq(OrderItem::getMerchantOrderId, orderTrackingInfoBO.getMerchantOrderId())
                .eq(OrderItem::getStatus, OrderStatus.PENDING_SHIPMENT.getStatus())
                .notIn(CollUtil.isNotEmpty(excludeSkuIdList), OrderItem::getSkuId, excludeSkuIdList)
                .update();
    }

    @Override
    public Boolean updateTracking(OrderTrackingInfoBO orderTrackingInfoBO, List<Long> excludeSkuIdList) {
        return this.lambdaUpdate()
                .set(OrderItem::getCarriers, orderTrackingInfoBO.getCarriers())
                .set(OrderItem::getTrackingNo, orderTrackingInfoBO.getTrackingNo())
                .set(OrderItem::getUpdateTime, LocalDateTime.now())
                .eq(OrderItem::getMerchantOrderId, orderTrackingInfoBO.getMerchantOrderId())
                .eq(OrderItem::getStatus, OrderStatus.PENDING_RECEIPT.getStatus())
                .notIn(CollUtil.isNotEmpty(excludeSkuIdList), OrderItem::getSkuId, excludeSkuIdList)
                .update();
    }

    @Override
    public OrderItem queryTracking(Long merchantOrderId) {
        return this.lambdaQuery().select(OrderItem::getCarriers, OrderItem::getTrackingNo, OrderItem::getDeliveryTime).eq(OrderItem::getMerchantOrderId, merchantOrderId).last("limit 1").one();
    }

    @Override
    public Boolean checkOrderCompleted(Long orderItemId) {
        OrderItem orderItem = this.lambdaQuery().select(OrderItem::getId, OrderItem::getOrderItemNo, OrderItem::getMerchantOrderId, OrderItem::getDeliveryTime, OrderItem::getStatus, OrderItem::getSkuId).eq(OrderItem::getId, orderItemId).one();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deliveryTime = orderItem.getDeliveryTime();
        if (orderItem.getStatus() == OrderStatus.PENDING_RECEIPT.getStatus() && Objects.nonNull(deliveryTime) && deliveryTime.plusDays(30).isBefore(now)) {
            completeOrder(orderItem);
            return true;
        }
        return false;
    }

    private void completeOrder(OrderItem orderItem) {
        this.lambdaUpdate().eq(OrderItem::getId, orderItem.getId()).set(OrderItem::getStatus, OrderStatus.COMPLETED.getStatus()).set(OrderItem::getUpdateTime, LocalDateTime.now()).update();
    }

    @Override
    public Boolean updateStatusByMerOrderId(Long merOrderId, Integer status, List<Long> skuIdList) {
        if (CollUtil.isEmpty(skuIdList)) {
            return this.lambdaUpdate().eq(OrderItem::getMerchantOrderId, merOrderId).set(OrderItem::getStatus, status).set(OrderItem::getUpdateTime, LocalDateTime.now()).update();
        }

        return this.lambdaUpdate().eq(OrderItem::getMerchantOrderId, merOrderId).in(OrderItem::getSkuId, skuIdList).set(OrderItem::getStatus, status).set(OrderItem::getUpdateTime, LocalDateTime.now()).update();
    }

    @Override
    public OrderItem queryById(Long id) {
        return this.getById(id);
    }

    @Override
    public List<OrderItem> queryByOrderId(Long orderId) {
        return this.lambdaQuery().eq(OrderItem::getOrderId, orderId).list();
    }
    public List<OrderItem> queryByTrack(String trackNo,String carriers){
       return this.lambdaQuery().eq(OrderItem::getTrackingNo,trackNo).eq(OrderItem::getCarriers,carriers).list();
    }
}

