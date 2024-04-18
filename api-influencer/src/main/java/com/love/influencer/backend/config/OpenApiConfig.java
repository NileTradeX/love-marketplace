package com.love.influencer.backend.config;

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
                        .title("Influencer Backend API")
                        .description("influencer backend apis")
                        .version("1.0.0")
                        .summary("influencer backend apis")
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
        return GroupedOpenApi.builder().group("InfluencerApi").displayName("InfluencerApi")
                .packagesToScan("com.love.influencer.backend.controller")
                .pathsToMatch("/influencer/**")
                .build();
    }
}
