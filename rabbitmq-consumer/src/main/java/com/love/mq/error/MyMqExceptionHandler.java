package com.love.mq.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

@Component("rabbitListenerErrorHandler")
public class MyMqExceptionHandler implements RabbitListenerErrorHandler {

    private final Logger logger = LoggerFactory.getLogger(MyMqExceptionHandler.class);

    @Override
    public Object handleError(org.springframework.amqp.core.Message amqpMessage, org.springframework.messaging.Message<?> message, ListenerExecutionFailedException exception) throws Exception {
        logger.error("===> {}", exception.getMessage(), exception);
        return message;
    }
}
