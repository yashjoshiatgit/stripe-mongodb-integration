package com.yashJoshi.stripe_payment_integration.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StripeResponse {
    private String status;
    private String message;
    private String sessionId;
    private String sessionUrl;
}
