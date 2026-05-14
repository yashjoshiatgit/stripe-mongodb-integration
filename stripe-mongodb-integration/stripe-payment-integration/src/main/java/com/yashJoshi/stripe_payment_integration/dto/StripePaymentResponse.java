package com.yashJoshi.stripe_payment_integration.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class StripePaymentResponse {
    private String id;
    private long amount;
    private long quantity;
    private String currency;
    private String productName;
    private String sessionId;
    private String sessionUrl;
    private String paymentStatus;
    private Instant createdAt;
    private Instant updatedAt;
}
