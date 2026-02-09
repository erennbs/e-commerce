package com.ecommerce.order.clients.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchaseRequest {

    @NotNull(message = "Product is required")
    private UUID productId;

    @Positive(message = "Quantity must be greater than zero")
    private double quantity;
}
