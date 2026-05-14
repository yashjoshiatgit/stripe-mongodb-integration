package com.yashJoshi.stripe_payment_integration.controller;

import com.yashJoshi.stripe_payment_integration.dto.StripePaymentResponse;
import com.yashJoshi.stripe_payment_integration.dto.StripeRequest;
import com.yashJoshi.stripe_payment_integration.dto.StripeResponse;
import com.yashJoshi.stripe_payment_integration.service.StripeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final StripeService stripeService;

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkOutProduct(@Valid @RequestBody StripeRequest stripeRequest) {
        StripeResponse response = stripeService.createCheckoutSession(stripeRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StripePaymentResponse> getPayment(@PathVariable String id) {
        return ResponseEntity.ok(stripeService.getPaymentById(id));
    }

    @GetMapping("/session/{sessionId}")
    public ResponseEntity<StripePaymentResponse> getPaymentBySession(@PathVariable String sessionId) {
        return ResponseEntity.ok(stripeService.getPaymentBySessionId(sessionId));
    }

    @GetMapping
    public ResponseEntity<Page<StripePaymentResponse>> listPayments(Pageable pageable) {
        return ResponseEntity.ok(stripeService.listPayments(pageable));
    }
}
