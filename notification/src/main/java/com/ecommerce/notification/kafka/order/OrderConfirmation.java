package com.ecommerce.notification.kafka.order;

import com.ecommerce.notification.kafka.payment.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderConfirmation {
    String reference;
    BigDecimal totalAmount;
    PaymentMethod paymentMethod;
    CustomerResponse customer;
    List<PurchaseResponse> products;
    OrderEventType eventType;
}
