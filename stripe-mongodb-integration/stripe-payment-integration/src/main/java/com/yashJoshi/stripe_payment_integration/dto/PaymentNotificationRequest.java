package com.yashJoshi.stripe_payment_integration.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentNotificationRequest {
    private String paymentId;
    private String sessionId;
    private String status;
    private long amount;
    private String currency;
    private String productName;
}
