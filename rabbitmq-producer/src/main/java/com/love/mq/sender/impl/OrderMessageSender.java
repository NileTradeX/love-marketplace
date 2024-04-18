package com.love.mq.sender.impl;

import com.love.mq.constants.RabbitMQConstants;
import com.love.mq.message.OrderCreateMessage;
import org.springframework.stereotype.Component;

@Component
public class OrderMessageSender extends DefaultMessageSender {

    public void sendOrderDelayMessage(OrderCreateMessage message) {
        super.sendMessage(RabbitMQConstants.OD_BIZ_EXCHANGE, RabbitMQConstants.OD_BIZ_KEY, message);
    }
}
