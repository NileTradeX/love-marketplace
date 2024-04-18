package com.love.mq.service.order;

import cn.hutool.core.collection.CollUtil;
import com.love.common.exception.BizException;
import com.love.goods.bo.ModifyGoodsSkuCommittedStockBO;
import com.love.goods.client.GoodsSkuFeignClient;
import com.love.mq.message.OrderCreateMessage;
import com.love.order.bo.OrderUpdateByOrderNoBO;
import com.love.order.bo.QuerySimpleOrderBO;
import com.love.order.client.OrderFeignClient;
import com.love.order.dto.OrderDTO;
import com.love.order.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderFeignClient orderFeignClient;
    private final GoodsSkuFeignClient goodsSkuFeignClient;

    public void orderReturnStock(OrderCreateMessage message) {
        logger.error("return stock for order: {}", message.getOrderNo());
        OrderDTO order = orderFeignClient.queryByOrderNo(QuerySimpleOrderBO.builder().orderNo(message.getOrderNo()).build());
        if (Objects.nonNull(order) && order.getStatus() == OrderStatus.AWAIT_PAYMENT.getStatus()) {

            if (CollUtil.isNotEmpty(message.getSukQtyMap())) {
                message.getSukQtyMap().forEach((skuId, qty) -> goodsSkuFeignClient.modifyCommittedStock(ModifyGoodsSkuCommittedStockBO.builder().skuId(skuId).committedStock(qty * -1).build()));
            } else {
                throw BizException.build("order mq message data is illegal");
            }

            OrderUpdateByOrderNoBO orderUpdateByOrderNoBO = new OrderUpdateByOrderNoBO();
            orderUpdateByOrderNoBO.setOrderNo(order.getOrderNo());
            orderUpdateByOrderNoBO.setStatus(OrderStatus.CLOSED.getStatus());
            orderUpdateByOrderNoBO.setReason("Overdue payment");
            orderUpdateByOrderNoBO.setCascade(true);
            orderFeignClient.updateByOrderNo(orderUpdateByOrderNoBO);
        }
    }
}
