package com.love.mq.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.content.ShoppingContent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Collections;

/**
 * @author Tony
 * 2023/5/14
 */
@Configuration
public class GoogleOAuthConfig {

    @Bean("shoppingContentService")
    public ShoppingContent createShoppingContent() throws IOException {

        GoogleCredential credentials = GoogleCredential.fromStream(getClass().getResourceAsStream("/content-api-key.json")).createScoped(Collections.singleton("https://www.googleapis.com/auth/content"));

        return new ShoppingContent.Builder(credentials.getTransport(), credentials.getJsonFactory(), credentials)
                .setApplicationName("love")
                .build();
    }

}
