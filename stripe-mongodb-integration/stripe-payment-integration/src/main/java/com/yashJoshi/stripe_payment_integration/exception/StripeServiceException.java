package com.yashJoshi.stripe_payment_integration.exception;

public class StripeServiceException extends RuntimeException {
    public StripeServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
