package com.yashJoshi.stripe_payment_integration.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;

public interface WebMvcConfigurer {
    void addCoreMappings(CorsRegistry registry);
}
