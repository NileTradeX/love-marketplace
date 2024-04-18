package com.love.mq.sender.impl;

import com.love.mq.constants.RabbitMQConstants;
import com.love.mq.message.GoodsUpdateMessage;
import org.springframework.stereotype.Component;

@Component
public class GoodsMessageSender extends DefaultMessageSender {

    public void sendGoodsUpdateMessage(GoodsUpdateMessage message) {
        super.sendMessage(RabbitMQConstants.GOODS_UPDATE_EXCHANGE, "", message);
    }

}
