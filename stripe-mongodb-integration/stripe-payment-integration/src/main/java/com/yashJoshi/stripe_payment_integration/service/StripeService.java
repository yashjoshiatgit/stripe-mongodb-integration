package com.yashJoshi.stripe_payment_integration.service;

import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.yashJoshi.stripe_payment_integration.dto.StripeRequest;
import com.yashJoshi.stripe_payment_integration.dto.StripeResponse;
import com.yashJoshi.stripe_payment_integration.entity.StripePayment;
import com.yashJoshi.stripe_payment_integration.repository.StripePaymentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secretKey}")
    private String secretKey;

    private final StripePaymentRepository stripePaymentRepository;

    public StripeService(StripePaymentRepository stripePaymentRepository) {
        this.stripePaymentRepository = stripePaymentRepository;
    }

    public StripeResponse createCheckoutSession(StripeRequest stripeFields) {
        Stripe.apiKey = secretKey;

        // Build Product Data
        SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                .setName(stripeFields.getName())
                .build();

        // Build Price Data
        SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency(stripeFields.getCurrency())
                .setUnitAmount(stripeFields.getAmount())
                .setProductData(productData)
                .build();

        // Build Line Item
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setPriceData(priceData)
                .setQuantity(stripeFields.getQuantity())
                .build();

        // Create Session
        SessionCreateParams params = SessionCreateParams.builder()
                .addLineItem(lineItem)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success")
                .setCancelUrl("http://localhost:8080/cancel")
                .build();

        try {
            Session session = Session.create(params);

            // Save to MongoDB
            StripePayment stripePayment = new StripePayment();
            stripePayment.setAmount(stripeFields.getAmount());
            stripePayment.setQuantity(stripeFields.getQuantity());
            stripePayment.setCurrency(stripeFields.getCurrency());
            stripePayment.setProductName(stripeFields.getName());
            stripePayment.setSessionId(session.getId());
            stripePayment.setSessionUrl(session.getUrl());
            stripePayment.setPaymentStatus(session.getPaymentStatus());

            stripePaymentRepository.save(stripePayment);

            return StripeResponse.builder()
                    .sessionId(session.getId())
                    .sessionUrl(session.getUrl())
                    .status(session.getPaymentStatus())
                    .message("Session created successfully")
                    .build();

        } catch (Exception e) {
            e.printStackTrace();
            return StripeResponse.builder()
                    .status("failed")
                    .message("Failed to create session: " + e.getMessage())
                    .build();
        }
    }
}