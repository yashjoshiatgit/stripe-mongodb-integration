package com.yashJoshi.stripe_payment_integration.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties(prefix = "stripe")
public class StripeProperties {

    @NotBlank
    private String secretKey;

    @Valid
    private Checkout checkout = new Checkout();

    @Data
    public static class Checkout {
        @NotBlank
        private String successUrl;
        @NotBlank
        private String cancelUrl;
    }
}
