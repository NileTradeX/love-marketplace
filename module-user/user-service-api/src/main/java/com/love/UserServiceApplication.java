package com.love;

import com.love.common.spring.deserializer.SimpleDateDeserializer;
import com.love.common.spring.deserializer.SimpleLocalDateTimeDeserializer;
import com.love.common.spring.formatter.String2DateFormatter;
import com.love.common.spring.formatter.String2LocalDateTimeFormatter;
import com.love.common.spring.serializer.SimpleDateSerializer;
import com.love.common.spring.serializer.SimpleLocalDateTimeSerializer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootApplication
@MapperScan("com.love.user.mapper")
@EnableDiscoveryClient
public class UserServiceApplication implements WebMvcConfigurer {

    @Value("${spring.application.name}")
    private String applicationName;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class);
    }

    @PostConstruct
    public void init() {
        System.setProperty("project.name", applicationName);
    }

    @Override
    public void addFormatters(@NonNull FormatterRegistry registry) {
        registry.addFormatter(new String2DateFormatter());
        registry.addFormatter(new String2LocalDateTimeFormatter());
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return mapperBuilder -> {
            mapperBuilder.serializerByType(Date.class, new SimpleDateSerializer());
            mapperBuilder.serializerByType(LocalDateTime.class, new SimpleLocalDateTimeSerializer());
            mapperBuilder.deserializerByType(Date.class, new SimpleDateDeserializer());
            mapperBuilder.deserializerByType(LocalDateTime.class, new SimpleLocalDateTimeDeserializer());
        };
    }
}
