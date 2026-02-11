package com.ecommerce.payment.dto;

import com.ecommerce.payment.model.Customer;
import com.ecommerce.payment.model.PaymentMethod;
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
    Customer customer;
}
