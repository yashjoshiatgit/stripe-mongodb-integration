package com.yashJoshi.stripe_payment_integration.config;

import jakarta.validation.constraints.AssertTrue;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Data
@Validated
@ConfigurationProperties(prefix = "app.cors")
public class CorsProperties {
    private List<String> allowedOrigins = new ArrayList<>();

    @AssertTrue(message = "CORS allowed origins must be explicit and not contain wildcards")
    public boolean isAllowedOriginsValid() {
        return allowedOrigins.stream().noneMatch(origin -> origin.contains("*"));
    }
}
