package com.ecommerce.order.mapper;

import com.ecommerce.order.dto.OrderLineRequest;
import com.ecommerce.order.dto.OrderLineResponse;
import com.ecommerce.order.model.Order;
import com.ecommerce.order.model.OrderLine;

public class OrderLineMapper {

    public static OrderLineResponse toDto(OrderLine orderLine) {
        return OrderLineResponse.builder()
                .id(orderLine.getId())
                .orderId(orderLine.getOrder().getId())
                .productId(orderLine.getProductId())
                .quantity(orderLine.getQuantity())
                .build();
    }

    public static OrderLine toEntity(OrderLineRequest orderLineRequest) {
        return OrderLine.builder()
                .id(orderLineRequest.getId())
                .productId(orderLineRequest.getProductId())
                .order(Order.builder()
                        .id(orderLineRequest.getOrderId())
                        .build()
                )
                .quantity(orderLineRequest.getQuantity())
                .build();
    }
}
