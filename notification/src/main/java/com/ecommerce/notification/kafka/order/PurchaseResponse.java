package com.ecommerce.notification.kafka.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PurchaseResponse {
    private UUID productId;
    private String name;
    private BigDecimal price;
    private double quantity;
}
