package com.ecommerce.order.clients.dtos;

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
public class PaymentRequest {
    UUID id;
    BigDecimal amount;
    PaymentMethod paymentMethod;
    UUID orderId;
    String orderReference;
    CustomerResponse customer;
}
