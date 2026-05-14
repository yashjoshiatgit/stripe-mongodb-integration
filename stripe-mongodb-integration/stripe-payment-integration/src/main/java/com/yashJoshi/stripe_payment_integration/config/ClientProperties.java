package com.yashJoshi.stripe_payment_integration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "clients")
public class ClientProperties {
    private Notification notification = new Notification();

    @Data
    public static class Notification {
        private String baseUrl = "http://localhost:8081";
        private boolean enabled = false;
    }
}
