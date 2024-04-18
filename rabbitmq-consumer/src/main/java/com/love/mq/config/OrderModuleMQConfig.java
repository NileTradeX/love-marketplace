package com.love.mq.config;

import com.love.mq.constants.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderModuleMQConfig {

    @Bean
    public Exchange orderDelayDLExchange() {
        return ExchangeBuilder.directExchange(RabbitMQConstants.OD_DLX_NAME).durable(true).build();
    }

    @Bean
    public Queue orderDelayDLQueue() {
        return QueueBuilder.durable(RabbitMQConstants.OD_DLQ_NAME).build();
    }

    @Bean
    public Binding orderDelayDLBinding() {
        return BindingBuilder.bind(orderDelayDLQueue()).to(orderDelayDLExchange()).with(RabbitMQConstants.OD_DL_RK_NAME).noargs();
    }

    @Bean
    public Exchange orderDelayBizExchange() {
        return ExchangeBuilder.directExchange(RabbitMQConstants.OD_BIZ_EXCHANGE).durable(true).alternate(RabbitMQConstants.OD_DLX_NAME).build();
    }

    @Bean
    public Queue orderDelayBizQueue() {
        return QueueBuilder.durable(RabbitMQConstants.OD_BIZ_QUEUE).deadLetterExchange(RabbitMQConstants.OD_DLX_NAME).deadLetterRoutingKey(RabbitMQConstants.OD_DL_RK_NAME).lazy().ttl(30 * 60 * 1000).build();
    }

    @Bean
    public Binding orderDelayBizBinding() {
        return BindingBuilder.bind(orderDelayBizQueue()).to(orderDelayBizExchange()).with(RabbitMQConstants.OD_BIZ_KEY).noargs();
    }
}
