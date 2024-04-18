package com.love.mq;

import com.love.common.spring.deserializer.SimpleDateDeserializer;
import com.love.common.spring.deserializer.SimpleLocalDateTimeDeserializer;
import com.love.common.spring.serializer.BigDecimalSerializer;
import com.love.common.spring.serializer.SimpleDateSerializer;
import com.love.common.spring.serializer.SimpleLocalDateTimeSerializer;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@EnableRabbit
@SpringBootApplication
@EnableFeignClients({"com.love"})
public class MqConsumerApplication {

    @Value("${spring.application.name}")
    private String applicationName;

    public static void main(String[] args) {
        SpringApplication.run(MqConsumerApplication.class);
    }

    @PostConstruct
    public void init() {
        System.setProperty("project.name", applicationName);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return mapperBuilder -> {
            mapperBuilder.serializerByType(BigDecimal.class, new BigDecimalSerializer());
            mapperBuilder.serializerByType(Date.class, new SimpleDateSerializer());
            mapperBuilder.serializerByType(LocalDateTime.class, new SimpleLocalDateTimeSerializer());
            mapperBuilder.deserializerByType(Date.class, new SimpleDateDeserializer());
            mapperBuilder.deserializerByType(LocalDateTime.class, new SimpleLocalDateTimeDeserializer());
        };
    }
}
