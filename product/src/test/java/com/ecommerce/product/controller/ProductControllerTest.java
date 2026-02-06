package com.ecommerce.product.controller;

import com.ecommerce.product.dto.ProductPurchaseRequest;
import com.ecommerce.product.dto.ProductPurchaseResponse;
import com.ecommerce.product.dto.ProductRequestDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.exception.ProductNotFoundException;
import com.ecommerce.product.exception.ProductPurchaseException;
import com.ecommerce.product.service.ProductService;
import jakarta.validation.Valid;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID productId;
    private ProductResponseDto productResponseDto;
    private ProductRequestDto productRequestDto;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();

        productResponseDto = ProductResponseDto.builder()
                .id(productId.toString())
                .name("Laptop")
                .description("Gaming laptop")
                .availableQuantity("10.0")
                .price("1500")
                .build();

        productRequestDto = ProductRequestDto.builder()
                .name("Laptop")
                .description("Gaming laptop")
                .availableQuantity(10.0)
                .price(BigDecimal.valueOf(1500))
                .categoryId(UUID.randomUUID())
                .build();
    }

    @Test
    @DisplayName("GET /api/v1/product - Should return list of products")
    void getProducts_ShouldReturnList() throws Exception {
        when(productService.getProducts()).thenReturn(List.of(productResponseDto));

        mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Laptop"))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("GET /api/v1/product/{id} - Should return product when found")
    void getProductById_ShouldReturnProduct_ValidId() throws Exception {
        when(productService.getProductById(productId)).thenReturn(productResponseDto);

        mockMvc.perform(get("/api/v1/product/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(productId.toString()));
    }

    @Test
    @DisplayName("GET /api/v1/product/{id} - Should return 400 when product not found")
    void getProductById_InvalidId_ShouldReturnBadRequest() throws Exception {
        String errorMessage = "Product not found with ID: " + productId;
        when(productService.getProductById(productId)).thenThrow(new ProductNotFoundException(errorMessage));

        mockMvc.perform(get("/api/v1/product/{id}", productId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Product not found!"));
    }

    @Test
    @DisplayName("POST /api/v1/product - Should create product and return 200")
    void createProduct_ValidRequest_ShouldReturnProduct() throws Exception {
        when(productService.createProduct(any(ProductRequestDto.class))).thenReturn(productResponseDto);

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    @DisplayName("POST /api/v1/product - Should return 400 when validation fails")
    void createProduct_InvalidRequest_ShouldReturnBadRequest() throws Exception {
        ProductRequestDto invalidRequest = new ProductRequestDto();

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/v1/product/purchase - Should purchase products successfully")
    void purchaseProducts_ValidRequest_ShouldReturnList() throws Exception {
        ProductPurchaseRequest request = ProductPurchaseRequest.builder()
                .productId(productId)
                .quantity(2)
                .build();

        ProductPurchaseResponse response = ProductPurchaseResponse.builder()
                .productId(productId.toString())
                .name("Laptop")
                .quantity("8.0")
                .price("1500")
                .build();

        when(productService.purchaseProducts(any())).thenReturn(List.of(response));

        mockMvc.perform(post("/api/v1/product/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(request))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productId").value(productId.toString()));
    }

    @Test
    @DisplayName("POST /api/v1/product/purchase - Should return 400 when purchase fails")
    void purchaseProducts_Fails_ShouldReturnBadRequest() throws Exception {
        ProductPurchaseRequest request = ProductPurchaseRequest.builder()
                .productId(productId)
                .quantity(20)
                .build();

        when(productService.purchaseProducts(any()))
                .thenThrow(new ProductPurchaseException("Insufficient quantity for product with Id: " + productId));

        mockMvc.perform(post("/api/v1/product/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(request))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Insufficient quantity for product with Id: " + productId));
    }

    @Test
    @DisplayName("DELETE /api/v1/product/{id} - Should return 204 No Content")
    void deleteProduct_ShouldReturnNoContent() throws Exception {
        doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(delete("/api/v1/product/{id}", productId))
                .andExpect(status().isNoContent());
    }
}

