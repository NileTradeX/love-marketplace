package com.love.mq.sender.impl;

import com.love.mq.sender.MqMessageSender;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultMessageSender implements MqMessageSender {

    private RabbitMessagingTemplate rabbitMessagingTemplate;

    @Autowired
    public void setRabbitMessagingTemplate(RabbitMessagingTemplate rabbitMessagingTemplate) {
        this.rabbitMessagingTemplate = rabbitMessagingTemplate;
    }

    @Override
    public void sendMessage(String exchange, String key, Object message) {
        rabbitMessagingTemplate.convertAndSend(exchange, key, message);
    }
}
