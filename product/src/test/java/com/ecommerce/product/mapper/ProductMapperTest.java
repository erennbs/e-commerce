package com.ecommerce.product.mapper;

import com.ecommerce.product.dto.CategoryResponseDto;
import com.ecommerce.product.dto.ProductPurchaseResponse;
import com.ecommerce.product.dto.ProductRequestDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.model.Category;
import com.ecommerce.product.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    @Test
    @DisplayName("toDto should map Product to ProductResponseDto correctly")
    void toDto_ShouldMapFieldsCorrectly() {
        UUID categoryId = UUID.randomUUID();
        Category category = Category.builder()
                .id(categoryId)
                .name("Electronics")
                .description("Electronic goods")
                .createdDate(LocalDate.now())
                .build();

        UUID productId = UUID.randomUUID();
        Product product = Product.builder()
                .id(productId)
                .name("Laptop")
                .description("Gaming laptop")
                .availableQuantity(5.0)
                .price(BigDecimal.valueOf(1500))
                .category(category)
                .createdDate(LocalDate.now())
                .build();

        ProductResponseDto dto = ProductMapper.toDto(product);

        assertNotNull(dto);
        assertEquals(productId.toString(), dto.getId());
        assertEquals("Laptop", dto.getName());
        assertEquals("Gaming laptop", dto.getDescription());
        assertEquals("5.0", dto.getAvailableQuantity());
        assertEquals("1500", dto.getPrice());
        CategoryResponseDto categoryDto = dto.getCategory();
        assertNotNull(categoryDto);
        assertEquals(categoryId.toString(), categoryDto.getId());
        assertEquals("Electronics", categoryDto.getName());
        assertEquals("Electronic goods", categoryDto.getDescription());
    }

    @Test
    @DisplayName("toPurchaseResponse should map Product to ProductPurchaseResponse correctly")
    void toPurchaseResponse_ShouldMapFieldsCorrectly() {
        UUID productId = UUID.randomUUID();
        Product product = Product.builder()
                .id(productId)
                .name("Phone")
                .description("Smartphone")
                .availableQuantity(3.0)
                .price(BigDecimal.valueOf(800))
                .category(Category.builder()
                        .id(UUID.randomUUID())
                        .name("Electronics")
                        .createdDate(LocalDate.now())
                        .build())
                .createdDate(LocalDate.now())
                .build();

        ProductPurchaseResponse response = ProductMapper.toPurchaseResponse(product);

        assertNotNull(response);
        assertEquals(productId.toString(), response.getProductId());
        assertEquals("Phone", response.getName());
        assertEquals("3.0", response.getQuantity());
        assertEquals("800", response.getPrice());
    }

    @Test
    @DisplayName("toEntity should map ProductRequestDto to Product correctly")
    void toEntity_ShouldMapFieldsCorrectly() {
        UUID categoryId = UUID.randomUUID();
        ProductRequestDto request = ProductRequestDto.builder()
                .name("Tablet")
                .description("Android tablet")
                .availableQuantity(7.0)
                .price(BigDecimal.valueOf(500))
                .categoryId(categoryId)
                .build();

        Product product = ProductMapper.toEntity(request);

        assertNotNull(product);
        assertEquals("Tablet", product.getName());
        assertEquals("Android tablet", product.getDescription());
        assertEquals(7.0, product.getAvailableQuantity());
        assertEquals(BigDecimal.valueOf(500), product.getPrice());
        assertNotNull(product.getCategory());
        assertEquals(categoryId, product.getCategory().getId());
        assertNotNull(product.getCreatedDate());
    }
}

