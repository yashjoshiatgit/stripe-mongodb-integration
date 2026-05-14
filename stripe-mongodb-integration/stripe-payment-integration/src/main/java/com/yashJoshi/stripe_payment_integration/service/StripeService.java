package com.yashJoshi.stripe_payment_integration.service;

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.yashJoshi.stripe_payment_integration.client.NotificationClient;
import com.yashJoshi.stripe_payment_integration.config.ClientProperties;
import com.yashJoshi.stripe_payment_integration.config.StripeProperties;
import com.yashJoshi.stripe_payment_integration.dto.PaymentNotificationRequest;
import com.yashJoshi.stripe_payment_integration.dto.StripeRequest;
import com.yashJoshi.stripe_payment_integration.dto.StripePaymentResponse;
import com.yashJoshi.stripe_payment_integration.dto.StripeResponse;
import com.yashJoshi.stripe_payment_integration.entity.StripePayment;
import com.yashJoshi.stripe_payment_integration.exception.ResourceNotFoundException;
import com.yashJoshi.stripe_payment_integration.exception.StripeServiceException;
import com.yashJoshi.stripe_payment_integration.repository.StripePaymentRepository;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StripeService {

    private final StripePaymentRepository stripePaymentRepository;
    private final StripeProperties stripeProperties;
    private final NotificationClient notificationClient;
    private final ClientProperties clientProperties;

    public StripeResponse createCheckoutSession(StripeRequest stripeFields) {
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
                .setSuccessUrl(stripeProperties.getCheckout().getSuccessUrl())
                .setCancelUrl(stripeProperties.getCheckout().getCancelUrl())
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

            StripePayment savedPayment = stripePaymentRepository.save(stripePayment);
            sendNotificationIfEnabled(savedPayment);

            return StripeResponse.builder()
                    .sessionId(session.getId())
                    .sessionUrl(session.getUrl())
                    .status(session.getPaymentStatus())
                    .paymentId(savedPayment.getId())
                    .message("Session created successfully")
                    .build();

        } catch (StripeException e) {
            throw new StripeServiceException("Failed to create Stripe checkout session", e);
        } catch (Exception e) {
            throw new StripeServiceException("Unexpected error while creating checkout session", e);
        }
    }

    public StripePaymentResponse getPaymentById(String id) {
        StripePayment payment = stripePaymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for id " + id));
        return toResponse(payment);
    }

    public StripePaymentResponse getPaymentBySessionId(String sessionId) {
        StripePayment payment = stripePaymentRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found for sessionId " + sessionId));
        return toResponse(payment);
    }

    public Page<StripePaymentResponse> listPayments(Pageable pageable) {
        return stripePaymentRepository.findAll(pageable).map(this::toResponse);
    }

    private StripePaymentResponse toResponse(StripePayment payment) {
        return StripePaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .quantity(payment.getQuantity())
                .currency(payment.getCurrency())
                .productName(payment.getProductName())
                .sessionId(payment.getSessionId())
                .sessionUrl(payment.getSessionUrl())
                .paymentStatus(payment.getPaymentStatus())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getUpdatedAt())
                .build();
    }

    private void sendNotificationIfEnabled(StripePayment payment) {
        if (!clientProperties.getNotification().isEnabled()) {
            return;
        }
        try {
            notificationClient.sendPaymentNotification(PaymentNotificationRequest.builder()
                    .paymentId(payment.getId())
                    .sessionId(payment.getSessionId())
                    .status(payment.getPaymentStatus())
                    .amount(payment.getAmount())
                    .currency(payment.getCurrency())
                    .productName(payment.getProductName())
                    .build());
        } catch (Exception ex) {
            log.warn("Failed to notify payment service for sessionId {}", payment.getSessionId(), ex);
        }
    }
}
