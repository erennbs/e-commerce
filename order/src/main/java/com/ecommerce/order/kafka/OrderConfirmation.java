package com.ecommerce.order.kafka;

import com.ecommerce.order.clients.dtos.CustomerResponse;
import com.ecommerce.order.clients.dtos.PurchaseResponse;
import com.ecommerce.order.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderConfirmation {
    String reference;
    BigDecimal totalAmount;
    PaymentMethod paymentMethod;
    CustomerResponse customer;
    List<PurchaseResponse> products;
    OrderEventType eventType;
}
