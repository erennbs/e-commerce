package com.ecommerce.order.mapper;

import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.model.Order;

public class OrderMapper {
    public static Order toEntity(OrderRequest orderRequest) {
        return Order.builder()
                .id(orderRequest.getId())
                .customerId(orderRequest.getCustomerId())
                .reference(orderRequest.getReference())
                .paymentMethod(orderRequest.getPaymentMethod())
                .totalAmount(orderRequest.getAmount())
                .build();
    }

    public static OrderResponse toDto(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .reference(order.getReference())
                .amount(order.getTotalAmount())
                .paymentMethod(order.getPaymentMethod())
                .customerId(order.getCustomerId())
                .build();
    }
}
