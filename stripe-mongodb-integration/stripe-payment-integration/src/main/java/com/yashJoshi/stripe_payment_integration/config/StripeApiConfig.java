package com.yashJoshi.stripe_payment_integration.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StripeApiConfig {

    private final StripeProperties stripeProperties;

    @PostConstruct
    public void configureStripe() {
        Stripe.apiKey = stripeProperties.getSecretKey();
    }
}
