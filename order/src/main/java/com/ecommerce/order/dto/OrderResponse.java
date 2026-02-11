package com.ecommerce.order.dto;

import com.ecommerce.order.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    UUID id;
    String reference;
    BigDecimal amount;
    PaymentMethod paymentMethod;
    UUID customerId;
}
