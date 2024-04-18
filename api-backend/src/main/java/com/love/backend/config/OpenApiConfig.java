package com.love.backend.config;

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
                        .title("Backend API")
                        .description("backend apis, include modules: SysManage,UserManage,GoodsManage,OrderManage etc.")
                        .version("1.0.0")
                        .summary("backend apis")
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
                .packagesToScan("com.love.backend.controller")
                .pathsToMatch("/sys/**")
                .build();
    }

    @Bean
    public GroupedOpenApi goodsApiGroup() {
        return GroupedOpenApi.builder().group("GoodsApi").displayName("GoodsApi")
                .packagesToScan("com.love.backend.controller")
                .pathsToMatch("/goods/**")
                .build();
    }

    @Bean
    public GroupedOpenApi orderApiGroup() {
        return GroupedOpenApi.builder().group("OrderApi").displayName("OrderApi")
                .packagesToScan("com.love.backend.controller")
                .pathsToMatch("/order/**")
                .build();
    }

    @Bean
    public GroupedOpenApi userApiGroup() {
        return GroupedOpenApi.builder().group("CustomerApi").displayName("CustomerApi")
                .packagesToScan("com.love.backend.controller")
                .pathsToMatch("/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi merchantApiGroup() {
        return GroupedOpenApi.builder().group("MerchantApi").displayName("MerchantApi")
                .packagesToScan("com.love.backend.controller")
                .pathsToMatch("/mer/**", "/merchant/**")
                .build();
    }

    @Bean
    public GroupedOpenApi reviewApiGroup() {
        return GroupedOpenApi.builder().group("ReviewApi").displayName("ReviewApi")
                .packagesToScan("com.love.backend.controller")
                .pathsToMatch("/review/**")
                .build();
    }

    @Bean
    public GroupedOpenApi uploadApiGroup() {
        return GroupedOpenApi.builder().group("UploadApi").displayName("UploadApi")
                .packagesToScan("com.love.backend.controller")
                .pathsToMatch("/file/**")
                .build();
    }


    @Bean
    public GroupedOpenApi influencerApiGroup() {
        return GroupedOpenApi.builder().group("InfluencerApi").displayName("InfluencerApi")
                .packagesToScan("com.love.backend.controller")
                .pathsToMatch("/influencer/**")
                .build();
    }
}
