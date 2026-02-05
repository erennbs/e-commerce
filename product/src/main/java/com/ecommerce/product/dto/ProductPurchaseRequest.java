package com.ecommerce.product.dto;

import jakarta.validation.constraints.NotBlank;
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
public class ProductPurchaseRequest {
    @NotNull(message = "Id is required")
    private UUID productId;

    @Positive(message = "Quantity must be greater than zero")
    private double quantity;
}
