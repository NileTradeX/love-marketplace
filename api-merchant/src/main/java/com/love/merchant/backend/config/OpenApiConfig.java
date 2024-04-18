package com.love.merchant.backend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Merchant Backend API")
                        .description("merchant backend apis, include modules: SysManage,GoodsManage,OrderManage etc.")
                        .version("1.0.0")
                        .summary("merchant backend apis")
                        .contact(new Contact()
                                .email("evan@love.com")
                                .name("evan")
                        ))
                .components(new Components()
                        .addSecuritySchemes("token", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name("token")))
                .addSecurityItem(new SecurityRequirement().addList("token"));
    }

    @Bean
    public GroupedOpenApi sysApiGroup() {
        return GroupedOpenApi.builder().group("SysApi").displayName("SysApi")
                .packagesToScan("com.love.merchant.backend.controller")
                .pathsToMatch("/mer/**")
                .build();
    }

    @Bean
    public GroupedOpenApi merchantApiGroup() {
        return GroupedOpenApi.builder().group("MerchantApi").displayName("MerchantApi")
                .packagesToScan("com.love.merchant.backend.controller")
                .pathsToMatch("/merchant/**")
                .build();
    }

    @Bean
    public GroupedOpenApi goodsApiGroup() {
        return GroupedOpenApi.builder().group("GoodsApi").displayName("GoodsApi")
                .packagesToScan("com.love.merchant.backend.controller")
                .pathsToMatch("/goods/**")
                .build();
    }

    @Bean
    public GroupedOpenApi orderApiGroup() {
        return GroupedOpenApi.builder().group("OrderApi").displayName("OrderApi")
                .packagesToScan("com.love.merchant.backend.controller")
                .pathsToMatch("/order/**")
                .build();
    }

    @Bean
    public GroupedOpenApi uploadApiGroup() {
        return GroupedOpenApi.builder().group("UploadApi").displayName("UploadApi")
                .packagesToScan("com.love.merchant.backend.controller")
                .pathsToMatch("/file/**")
                .build();
    }
}
