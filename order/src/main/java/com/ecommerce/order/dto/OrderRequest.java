package com.ecommerce.order.dto;

import com.ecommerce.order.model.PaymentMethod;
import com.ecommerce.order.clients.dtos.PurchaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    UUID id;

    String reference;

    @Positive(message = "Order amount must be greater than zero")
    BigDecimal amount;

    @NotNull(message = "Payment method is required")
    PaymentMethod paymentMethod;

    @NotNull(message = "Customer is required")
    UUID customerId;

    @NotEmpty(message = "You should add at least one product")
    List<PurchaseRequest> products;
}
