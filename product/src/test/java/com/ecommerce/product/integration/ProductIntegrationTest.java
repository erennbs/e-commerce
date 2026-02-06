package com.ecommerce.product.integration;

import com.ecommerce.product.dto.ProductPurchaseRequest;
import com.ecommerce.product.dto.ProductRequestDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.CategoryRepository;
import com.ecommerce.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
class ProductIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanUp() {
        productRepository.deleteAll();
    }

    @Test
    @DisplayName("Full Lifecycle: Create, Get, Purchase, and Delete Product")
    void productLifecycleIntegrationTest() throws Exception {
        // Ensure there is at least one category from Flyway data
        UUID categoryId = categoryRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No categories found in database"))
                .getId();

        // 1. Create a Product
        ProductRequestDto request = ProductRequestDto.builder()
                .name("Integration Laptop")
                .description("High-end gaming laptop")
                .availableQuantity(10.0)
                .price(BigDecimal.valueOf(2000))
                .categoryId(categoryId)
                .build();

        MvcResult createResult = mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        ProductResponseDto createdProduct = objectMapper.readValue(
                createResult.getResponse().getContentAsString(),
                ProductResponseDto.class
        );

        UUID createdProductId = UUID.fromString(createdProduct.getId());

        // 2. Verify existence in Database
        assertThat(productRepository.findById(createdProductId)).isPresent();

        // 3. Get the Product by ID
        mockMvc.perform(get("/api/v1/product/{id}", createdProductId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Integration Laptop"))
                .andExpect(jsonPath("$.price").value("2000"));

        // 4. Purchase the Product
        ProductPurchaseRequest purchaseRequest = ProductPurchaseRequest.builder()
                .productId(createdProductId)
                .quantity(2)
                .build();

        mockMvc.perform(post("/api/v1/product/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(purchaseRequest))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(createdProductId.toString()));

        // 5. Verify quantity was reduced in Database
        Product updatedProduct = productRepository.findById(createdProductId).orElseThrow();
        assertThat(updatedProduct.getAvailableQuantity()).isEqualTo(8.0);

        // 6. Delete the Product
        mockMvc.perform(delete("/api/v1/product/{id}", createdProductId))
                .andExpect(status().isNoContent());

        // 7. Verify it's gone
        assertThat(productRepository.findById(createdProductId)).isEmpty();
    }

    @Test
    @DisplayName("Validation: Should fail when required fields are missing")
    void createProduct_InvalidRequest_ReturnsBadRequest() throws Exception {
        ProductRequestDto invalidRequest = new ProductRequestDto();

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Purchase: Should fail when quantity is too high")
    void purchaseProduct_TooHighQuantity_ReturnsBadRequest() throws Exception {
        UUID categoryId = categoryRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No categories found in database"))
                .getId();

        // Create product with small quantity
        ProductRequestDto request = ProductRequestDto.builder()
                .name("Limited Stock Item")
                .description("Only few in stock")
                .availableQuantity(1.0)
                .price(BigDecimal.valueOf(100))
                .categoryId(categoryId)
                .build();

        MvcResult createResult = mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        ProductResponseDto createdProduct = objectMapper.readValue(
                createResult.getResponse().getContentAsString(),
                ProductResponseDto.class
        );

        UUID createdProductId = UUID.fromString(createdProduct.getId());

        ProductPurchaseRequest purchaseRequest = ProductPurchaseRequest.builder()
                .productId(createdProductId)
                .quantity(5)
                .build();

        mockMvc.perform(post("/api/v1/product/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(purchaseRequest))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Insufficient quantity for product with Id: " + createdProductId));
    }
}

