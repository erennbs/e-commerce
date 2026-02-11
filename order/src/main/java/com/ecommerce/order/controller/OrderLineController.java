package com.ecommerce.order.controller;

import com.ecommerce.order.dto.OrderLineResponse;
import com.ecommerce.order.service.OrderLineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order-lines")
@RequiredArgsConstructor
public class OrderLineController {
    private final OrderLineService orderLineService;

    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderLineResponse>> getOrderById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderLineService.GetAllByOrderId(orderId));
    }
}
