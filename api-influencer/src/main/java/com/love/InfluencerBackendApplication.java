package com.love;

import com.love.api.common.filter.GlobalParamFilter;
import com.love.api.common.filter.TokenFilter;
import com.love.common.Constants;
import com.love.common.spring.deserializer.SimpleDateDeserializer;
import com.love.common.spring.deserializer.SimpleLocalDateTimeDeserializer;
import com.love.common.spring.formatter.String2DateFormatter;
import com.love.common.spring.formatter.String2LocalDateTimeFormatter;
import com.love.common.spring.serializer.BigDecimalSerializer;
import com.love.common.spring.serializer.SimpleDateSerializer;
import com.love.common.spring.serializer.SimpleLocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

@SpringBootApplication
@EnableFeignClients({"com.love"})
@EnableAsync
public class InfluencerBackendApplication implements WebMvcConfigurer {

    @Value("${spring.application.name}")
    private String applicationName;

    @Value("${cors.host:*}")
    private String corsHost;

    public static void main(String[] args) {
        SpringApplication.run(InfluencerBackendApplication.class);
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
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setMaxAge(120L);
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setExposedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("content-type", "token", "x-timestamp"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedOriginPatterns(Arrays.asList(corsHost.split(",")));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        FilterRegistrationBean<CorsFilter> filterRegistration = new FilterRegistrationBean<>();
        CorsFilter corsFilter = new CorsFilter(source);
        filterRegistration.setFilter(corsFilter);
        filterRegistration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistration;
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

    @Bean
    public GlobalParamFilter globalParamFilter() {
        GlobalParamFilter globalParamFilter = new GlobalParamFilter();
        globalParamFilter.setFieldList(Arrays.asList(Constants.UID, Constants.USER_ID));
        return globalParamFilter;
    }

    @Bean
    public TokenFilter tokenFilter(HandlerExceptionResolver handlerExceptionResolver) {
        TokenFilter tokenFilter = new TokenFilter();
        tokenFilter.setHandlerExceptionResolver(handlerExceptionResolver);
        tokenFilter.setWhitelist(Arrays.asList("/swagger-ui.*", "/v3/.*", "/doc.html", "/webjars/.*", "/influencer/login", "/influencer/queryByCode", "/influencer/register"));
        return tokenFilter;
    }
}
