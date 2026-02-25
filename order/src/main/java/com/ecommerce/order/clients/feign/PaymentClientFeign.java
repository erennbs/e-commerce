package com.ecommerce.order.clients.feign;

import com.ecommerce.order.clients.PaymentClient;
import com.ecommerce.order.clients.dtos.PaymentRequest;
import com.ecommerce.order.clients.dtos.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE", path = "/api/v1/payments")
public interface PaymentClientFeign extends PaymentClient {
    @PostMapping
    PaymentResponse requestOrderPayment(@RequestBody PaymentRequest paymentRequest);
}
