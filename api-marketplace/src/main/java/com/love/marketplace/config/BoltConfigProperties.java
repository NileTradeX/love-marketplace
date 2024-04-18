package com.love.marketplace.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = BoltConfigProperties.PREFIX)
public class BoltConfigProperties {
    public static final String PREFIX = "bolt";
    public static final String BOLT_HMAC_HEADER = "X-Bolt-Hmac-Sha256";
    public static final String HMAC_SHA256 = "HmacSHA256";

    private String apiUrl;

    private String signingSecret;

    private Config account;

    private Config checkout;

    public byte[] getHmacKey() {
        return signingSecret.getBytes();
    }

    @Getter
    @Setter
    public static class Config {
        private String publishableKey;
        private String apiKey;
    }

}
