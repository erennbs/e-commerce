package com.ecommerce.product.mapper;

import com.ecommerce.product.dto.ProductPurchaseResponse;
import com.ecommerce.product.dto.ProductRequestDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.model.Category;
import com.ecommerce.product.model.Product;

import java.time.LocalDate;

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

    public static ProductPurchaseResponse toPurchaseResponse(Product product) {
        return ProductPurchaseResponse.builder()
                .productId(product.getId().toString())
                .name(product.getName())
                .quantity(product.getAvailableQuantity().toString())
                .price(product.getPrice().toString())
                .build();
    }

    public static Product toEntity(ProductRequestDto productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .availableQuantity(productRequest.getAvailableQuantity())
                .price(productRequest.getPrice())
                .category(Category.builder()
                        .id(productRequest.getCategoryId())
                        .build())
                .createdDate(LocalDate.now())
                .build();
    }
}
