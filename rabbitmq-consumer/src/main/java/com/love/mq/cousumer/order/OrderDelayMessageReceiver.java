package com.love.mq.cousumer.order;

import com.love.mq.constants.RabbitMQConstants;
import com.love.mq.message.OrderCreateMessage;
import com.love.mq.service.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class OrderDelayMessageReceiver {


    private final OrderService orderService;

    @RabbitListener(queues = RabbitMQConstants.OD_DLQ_NAME)
    public void orderDelayMessageReceiver(OrderCreateMessage orderCreateMessage) {
        orderService.orderReturnStock(orderCreateMessage);
    }
}
