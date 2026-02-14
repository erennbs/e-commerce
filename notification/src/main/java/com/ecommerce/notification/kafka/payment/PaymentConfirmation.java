package com.ecommerce.notification.kafka.payment;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentConfirmation {
    String orderReference;
    BigDecimal amount;
    PaymentMethod paymentMethod;
    String customerFirstName;
    String customerLastName;
    String customerEmail;
}
