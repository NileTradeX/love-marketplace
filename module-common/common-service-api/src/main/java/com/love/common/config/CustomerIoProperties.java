package com.love.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "customer-io")
public class CustomerIoProperties {

    private String apiToken;

    private String fromEmail;

    private String apiUrl;

    private TemplateAlias templateAlias;
}
