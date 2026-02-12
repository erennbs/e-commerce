package com.ecommerce.order.clients;

import com.ecommerce.order.clients.dtos.PaymentRequest;
import com.ecommerce.order.clients.dtos.PaymentResponse;

public interface PaymentClient {
    PaymentResponse requestOrderPayment(PaymentRequest paymentRequest);
}
