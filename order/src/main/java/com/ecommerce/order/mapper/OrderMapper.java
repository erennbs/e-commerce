package com.ecommerce.order.mapper;

import com.ecommerce.order.dto.OrderRequest;
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
}
