package com.love.mq.sender;

public interface MqMessageSender {

    void sendMessage(String exchange, String key, Object message);

}
