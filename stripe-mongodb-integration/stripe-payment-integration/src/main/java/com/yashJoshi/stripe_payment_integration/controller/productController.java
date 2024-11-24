package com.yashJoshi.stripe_payment_integration.controller;

import com.yashJoshi.stripe_payment_integration.dto.StripeRequest;
import com.yashJoshi.stripe_payment_integration.dto.StripeResponse;
import com.yashJoshi.stripe_payment_integration.service.StripeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class productController {

    private final StripeService stripeService;

    @PostMapping("/checkout")
    public ResponseEntity<StripeResponse> checkOutProduct(@Valid @RequestBody StripeRequest stripeRequest) {
        StripeResponse response = stripeService.createCheckoutSession(stripeRequest);
        return ResponseEntity.ok(response);
    }
}
