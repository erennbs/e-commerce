package com.ecommerce.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductPurchaseResponse {
    private String productId;
    private String name;
    private String price;
    private String quantity;
}
