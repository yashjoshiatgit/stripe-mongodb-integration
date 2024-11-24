package com.yashJoshi.stripe_payment_integration.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StripeRequest {

    @Min(value = 1, message = "Amount must be greater than 0")
    private long amount;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private long quantity;

    @NotBlank(message = "Currency cannot be blank")
    private String currency;

    @NotBlank(message = "Product name cannot be blank")
    private String name;
}
