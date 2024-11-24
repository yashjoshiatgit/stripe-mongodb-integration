package com.yashJoshi.stripe_payment_integration.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "stripe_payments")
public class StripePayment {

    @Id
    private String id;
    private long amount;
    private long quantity;
    private String currency;
    private String productName;
    private String sessionId;
    private String sessionUrl;
    private String paymentStatus;
}
