package com.ecommerce.payment.mapper;

import com.ecommerce.payment.dto.PaymentRequest;
import com.ecommerce.payment.dto.PaymentResponse;
import com.ecommerce.payment.model.Payment;

public class PaymentMapper {
    public static Payment toEntity(PaymentRequest paymentRequest) {
        return Payment.builder()
                .id(paymentRequest.getId())
                .amount(paymentRequest.getAmount())
                .paymentMethod(paymentRequest.getPaymentMethod())
                .orderId(paymentRequest.getOrderId())
                .build();
    }

    public static PaymentResponse toDto(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .orderId(payment.getOrderId())
                .build();
    }
}
