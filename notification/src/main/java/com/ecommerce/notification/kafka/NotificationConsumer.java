package com.ecommerce.notification.kafka;

import com.ecommerce.notification.kafka.order.OrderConfirmation;
import com.ecommerce.notification.kafka.payment.PaymentConfirmation;
import com.ecommerce.notification.model.Notification;
import com.ecommerce.notification.model.NotificationType;
import com.ecommerce.notification.repository.NotificationRepository;
import com.ecommerce.notification.service.EmailService;
import com.ecommerce.notification.service.NotificationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumer {
    private final NotificationService notificationService;
    private final EmailService emailService;

    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotification(PaymentConfirmation paymentConfirmation) throws MessagingException {
        log.info("Received payment confirmation: {}", paymentConfirmation);
        notificationService.saveNotification(
                Notification.builder()
                        .type(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .paymentConfirmation(paymentConfirmation)
                        .build());

        emailService.sendPaymentSuccessEmail(
                paymentConfirmation.getCustomerEmail(),
                paymentConfirmation.getCustomerFirstName(),
                paymentConfirmation.getAmount(),
                paymentConfirmation.getOrderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotification(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info("Received order confirmation: {}", orderConfirmation);
        notificationService.saveNotification(
                Notification.builder()
                        .type(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .orderConfirmation(orderConfirmation)
                        .build());

        emailService.sendOrderConfirmationEmail(
                orderConfirmation.getCustomer().getEmail(),
                orderConfirmation.getCustomer().getFirstName(),
                orderConfirmation.getTotalAmount(),
                orderConfirmation.getReference(),
                orderConfirmation.getProducts()
        );
    }
}
