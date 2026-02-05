package com.ecommerce.product.dto;

import com.ecommerce.product.model.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponseDto {
    private String id;
    private String name;
    private String description;
    private String availableQuantity;
    private String price;
    private CategoryResponseDto category;
}
