package com.ecommerce.product.mapper;

import com.ecommerce.product.dto.CategoryResponseDto;
import com.ecommerce.product.model.Category;

public class CategoryMapper {
    public static CategoryResponseDto toDto(Category category) {
        return CategoryResponseDto.builder()
                .id(category.getId().toString())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
