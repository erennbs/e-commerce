package com.ecommerce.order.service;

import com.ecommerce.order.clients.CustomerClient;
import com.ecommerce.order.clients.ProductClient;
import com.ecommerce.order.dto.OrderLineRequest;
import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.dto.OrderResponse;
import com.ecommerce.order.exception.OrderException;
import com.ecommerce.order.kafka.OrderConfirmation;
import com.ecommerce.order.kafka.OrderEventType;
import com.ecommerce.order.kafka.OrderProducer;
import com.ecommerce.order.mapper.OrderMapper;
import com.ecommerce.order.clients.dtos.PurchaseRequest;
import com.ecommerce.order.repository.OrderRepository;

import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderLineService orderLineService;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderProducer orderProducer;

    @Transactional
    public UUID createOrder(OrderRequest request) {
        var customer = customerClient.getCustomerById(request.getCustomerId())
                .orElseThrow(() -> new OrderException("Can't create order: Customer not found with id - " + request.getCustomerId()));

        var purchasedProducts = productClient.purchaseProducts(request.getProducts());

        var order = orderRepository.save(OrderMapper.toEntity(request));

        for(PurchaseRequest purchaseRequest : request.getProducts()) {
            orderLineService.createOrderLine(new OrderLineRequest(
                    null, order.getId(), purchaseRequest.getProductId(), purchaseRequest.getQuantity()
            ));
        }

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.getReference(),
                        request.getAmount(),
                        request.getPaymentMethod(),
                        customer,
                        purchasedProducts,
                        OrderEventType.ORDER_CREATED
                )
        );

        return order.getId();
    }

    public List<OrderResponse> getOrders() {
        return orderRepository.findAll().stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    public OrderResponse getOrder(UUID id) {
        return orderRepository.findById(id)
                .map(OrderMapper::toDto)
                .orElseThrow(() -> new OrderException("No order found with id: " + id));
    }
}
