package com.love.marketplace.config;

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
                        .title("Frontend API")
                        .description("frontend apis, include modules: UserManage,GoodsManage,OrderManage etc.")
                        .version("1.0.0")
                        .summary("frontend apis")
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
    public GroupedOpenApi goodsApiGroup() {
        return GroupedOpenApi.builder().group("GoodsApi").displayName("GoodsApi")
                .packagesToScan("com.love.marketplace.controller")
                .pathsToMatch("/goods/**")
                .build();
    }

    @Bean
    public GroupedOpenApi orderApiGroup() {
        return GroupedOpenApi.builder().group("OrderApi").displayName("OrderApi")
                .packagesToScan("com.love.marketplace.controller")
                .pathsToMatch("/order/**")
                .build();
    }

    @Bean
    public GroupedOpenApi paymentApiGroup() {
        return GroupedOpenApi.builder().group("PaymentApi").displayName("PaymentApi")
                .packagesToScan("com.love.marketplace.controller")
                .pathsToMatch("/payment/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userApiGroup() {
        return GroupedOpenApi.builder().group("UserApi").displayName("UserApi")
                .packagesToScan("com.love.marketplace.controller")
                .pathsToMatch("/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi uploadApiGroup() {
        return GroupedOpenApi.builder().group("UploadApi").displayName("UploadApi")
                .packagesToScan("com.love.marketplace.controller")
                .pathsToMatch("/file/**")
                .build();
    }

    @Bean
    public GroupedOpenApi reviewApiGroup() {
        return GroupedOpenApi.builder().group("ReviewApi").displayName("ReviewApi")
                .packagesToScan("com.love.marketplace.controller")
                .pathsToMatch("/review/**")
                .build();
    }

    @Bean
    public GroupedOpenApi influencerApiGroup() {
        return GroupedOpenApi.builder().group("InfluencerApi").displayName("InfluencerApi")
                .packagesToScan("com.love.marketplace.controller")
                .pathsToMatch("/influencer/**")
                .build();
    }

    @Bean
    public GroupedOpenApi boltCallbackApiGroup() {
        return GroupedOpenApi.builder().group("BoltCallbackApi").displayName("BoltCallbackApi")
                .packagesToScan("com.love.marketplace.controller")
                .pathsToMatch("/bolt/**")
                .build();
    }

    @Bean
    public GroupedOpenApi giftApiGroup() {
        return GroupedOpenApi.builder().group("GiftApi").displayName("GiftApi")
                .packagesToScan("com.love.marketplace.controller")
                .pathsToMatch("/gift/**")
                .build();
    }
}
