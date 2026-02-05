package com.ecommerce.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequestDto {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @Positive(message = "Quantity must be greater than zero")
    private Double availableQuantity;

    @Positive(message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Category id is required")
    private java.util.UUID categoryId;
}
