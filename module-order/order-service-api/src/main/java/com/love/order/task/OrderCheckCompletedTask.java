package com.love.order.task;

import com.love.order.bo.OrderUpdateByOrderNoBO;
import com.love.order.dto.AfterSalesRecordDetailDTO;
import com.love.order.dto.MerchantOrderDTO;
import com.love.order.entity.Order;
import com.love.order.entity.OrderItem;
import com.love.order.enums.AfterSaleStatus;
import com.love.order.enums.OrderStatus;
import com.love.order.service.AfterSalesRecordService;
import com.love.order.service.MerchantOrderService;
import com.love.order.service.OrderItemService;
import com.love.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderCheckCompletedTask {

    private final Logger logger = LoggerFactory.getLogger(OrderCheckCompletedTask.class);
    private static final String KEY = "order::completed::check";

    private final OrderService orderService;
    private final OrderItemService orderItemService;
    private final MerchantOrderService merchantOrderService;
    private final StringRedisTemplate stringRedisTemplate;
    private final AfterSalesRecordService afterSalesRecordService;

    @Scheduled(cron = "0 0 3 * * ?")
    public void checkCompleted() {
        logger.info("===> begin : check order completed at {}", LocalDateTime.now());
        Boolean value = stringRedisTemplate.opsForValue().setIfAbsent(KEY, "1", 5, TimeUnit.MINUTES);
        if (Boolean.TRUE.equals(value)) {
            List<Order> orders = orderService.queryByStatus(OrderStatus.PENDING_RECEIPT.getStatus());
            if (!orders.isEmpty()) {
                for (Order order : orders) {
                    List<OrderItem> orderItems = orderItemService.queryByOrderId(order.getId());
                    Map<Long, List<OrderItem>> map = orderItems.stream().collect(Collectors.groupingBy(OrderItem::getMerchantOrderId));
                    AtomicBoolean totalCompleted = new AtomicBoolean(true);
                    map.forEach((merchantOrderId, items) -> {
                        boolean completed = true;
                        for (OrderItem item : items) {
                            completed = completed && (item.getStatus() == OrderStatus.COMPLETED.getStatus());
                        }
                        if (completed) {
                            merchantOrderService.updateStatusById(merchantOrderId, OrderStatus.COMPLETED.getStatus(), null);
                        }else{
                            totalCompleted.set(false);
                        }
                    });

                    if (totalCompleted.get()) {
                        OrderUpdateByOrderNoBO updateByOrderNoBO = new OrderUpdateByOrderNoBO();
                        updateByOrderNoBO.setOrderNo(order.getOrderNo());
                        updateByOrderNoBO.setStatus(OrderStatus.COMPLETED.getStatus());
                        orderService.updateByOrderNo(updateByOrderNoBO);
                    }
                }
            }
        }
        logger.info("===> end : check order completed at {}", LocalDateTime.now());
    }
}
