package com.yashJoshi.stripe_payment_integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@ConfigurationPropertiesScan
public class StripePaymentIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(StripePaymentIntegrationApplication.class, args);
	}

}
