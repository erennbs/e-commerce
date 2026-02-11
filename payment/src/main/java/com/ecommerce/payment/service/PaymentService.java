package com.ecommerce.payment.service;

import com.ecommerce.payment.dto.PaymentRequest;
import com.ecommerce.payment.dto.PaymentResponse;
import com.ecommerce.payment.kafka.NotificationProducer;
import com.ecommerce.payment.kafka.PaymentNotificationRequest;
import com.ecommerce.payment.mapper.PaymentMapper;
import com.ecommerce.payment.model.Payment;
import com.ecommerce.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final NotificationProducer notificationProducer;

    public PaymentResponse createPayment(PaymentRequest paymentRequest) {
        Payment payment = paymentRepository.save(PaymentMapper.toEntity(paymentRequest));

        notificationProducer.sendNotification(
                PaymentNotificationRequest.builder()
                        .orderReference(paymentRequest.getOrderReference())
                        .amount(paymentRequest.getAmount())
                        .paymentMethod(paymentRequest.getPaymentMethod())
                        .customerFirstName(paymentRequest.getCustomer().getFirstName())
                        .customerLastName(paymentRequest.getCustomer().getLastName())
                        .customerEmail(paymentRequest.getCustomer().getEmail())
                        .build());

        return PaymentMapper.toDto(payment);
    }
}
