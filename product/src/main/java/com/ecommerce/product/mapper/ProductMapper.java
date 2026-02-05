package com.ecommerce.product.mapper;

import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.model.Product;

public class ProductMapper {
    public static ProductResponseDto toDto(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId().toString())
                .name(product.getName())
                .description(product.getDescription())
                .availableQuantity(product.getAvailableQuantity().toString())
                .price(product.getPrice().toString())
                .category(CategoryMapper.toDto(product.getCategory()))
                .build();
    }
}
