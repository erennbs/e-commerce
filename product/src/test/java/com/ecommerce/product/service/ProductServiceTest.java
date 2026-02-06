package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductPurchaseRequest;
import com.ecommerce.product.dto.ProductRequestDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.exception.ProductNotFoundException;
import com.ecommerce.product.exception.ProductPurchaseException;
import com.ecommerce.product.model.Category;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product testProduct;
    private ProductRequestDto productRequestDto;

    @BeforeEach
    void setUp() {
        UUID categoryId = UUID.randomUUID();
        Category category = Category.builder()
                .id(categoryId)
                .name("Electronics")
                .description("Electronic items")
                .createdDate(LocalDate.now())
                .build();

        UUID productId = UUID.randomUUID();
        testProduct = Product.builder()
                .id(productId)
                .name("Laptop")
                .description("Gaming laptop")
                .availableQuantity(10.0)
                .price(BigDecimal.valueOf(1500))
                .category(category)
                .createdDate(LocalDate.now())
                .build();

        productRequestDto = ProductRequestDto.builder()
                .name("Laptop")
                .description("Gaming laptop")
                .availableQuantity(10.0)
                .price(BigDecimal.valueOf(1500))
                .categoryId(categoryId)
                .build();
    }

    @Nested
    class GetProductsTests {

        @Test
        @DisplayName("Should return a list of ProductResponseDto")
        void getProducts_ReturnList() {
            when(productRepository.findAll()).thenReturn(List.of(testProduct));

            List<ProductResponseDto> result = productService.getProducts();

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(testProduct.getName(), result.get(0).getName());
            verify(productRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Should return a single ProductResponseDto when ID exists")
        void getProductById_ValidId_ReturnsDto() {
            when(productRepository.findById(testProduct.getId())).thenReturn(Optional.of(testProduct));

            ProductResponseDto result = productService.getProductById(testProduct.getId());

            assertNotNull(result);
            assertEquals(testProduct.getName(), result.getName());
            verify(productRepository).findById(testProduct.getId());
        }

        @Test
        @DisplayName("Should throw ProductNotFoundException when ID does not exist")
        void getProductById_InvalidId_ThrowsException() {
            when(productRepository.findById(testProduct.getId())).thenReturn(Optional.empty());

            assertThrows(ProductNotFoundException.class,
                    () -> productService.getProductById(testProduct.getId()));
            verify(productRepository).findById(testProduct.getId());
        }
    }

    @Nested
    class CreateProductTests {

        @Test
        @DisplayName("Should create product successfully with valid request")
        void createProduct_ValidRequest_ReturnsDto() {
            when(productRepository.save(any(Product.class))).thenReturn(testProduct);

            ProductResponseDto result = productService.createProduct(productRequestDto);

            assertNotNull(result);
            assertEquals(testProduct.getName(), result.getName());
            verify(productRepository).save(any(Product.class));
        }
    }

    @Nested
    class PurchaseProductsTests {

        @Test
        @DisplayName("Should purchase products successfully when all products exist and have sufficient quantity")
        void purchaseProducts_ValidRequest_ReturnsPurchaseResponses() {
            UUID productId1 = UUID.randomUUID();
            UUID productId2 = UUID.randomUUID();

            Product product1 = Product.builder()
                    .id(productId1)
                    .name("Phone")
                    .description("Smartphone")
                    .availableQuantity(10.0)
                    .price(BigDecimal.valueOf(800))
                    .category(testProduct.getCategory())
                    .createdDate(LocalDate.now())
                    .build();

            Product product2 = Product.builder()
                    .id(productId2)
                    .name("Tablet")
                    .description("Android tablet")
                    .availableQuantity(15.0)
                    .price(BigDecimal.valueOf(500))
                    .category(testProduct.getCategory())
                    .createdDate(LocalDate.now())
                    .build();

            ProductPurchaseRequest request1 = ProductPurchaseRequest.builder()
                    .productId(productId1)
                    .quantity(2)
                    .build();

            ProductPurchaseRequest request2 = ProductPurchaseRequest.builder()
                    .productId(productId2)
                    .quantity(3)
                    .build();

            List<ProductPurchaseRequest> requests = List.of(request1, request2);

            when(productRepository.findAllByIdInOrderById(any())).thenReturn(List.of(product1, product2));

            var result = productService.purchaseProducts(requests);

            assertNotNull(result);
            assertEquals(2, result.size());
            verify(productRepository, times(1)).findAllByIdInOrderById(any());
            verify(productRepository, times(1)).save(product1);
            verify(productRepository, times(1)).save(product2);
        }

        @Test
        @DisplayName("Should throw ProductPurchaseException when any product ID does not exist")
        void purchaseProducts_MissingProduct_ThrowsException() {
            UUID existingProductId = UUID.randomUUID();
            UUID missingProductId = UUID.randomUUID();

            Product existingProduct = Product.builder()
                    .id(existingProductId)
                    .name("Phone")
                    .description("Smartphone")
                    .availableQuantity(10.0)
                    .price(BigDecimal.valueOf(800))
                    .category(testProduct.getCategory())
                    .createdDate(LocalDate.now())
                    .build();

            ProductPurchaseRequest request1 = ProductPurchaseRequest.builder()
                    .productId(existingProductId)
                    .quantity(2)
                    .build();

            ProductPurchaseRequest request2 = ProductPurchaseRequest.builder()
                    .productId(missingProductId)
                    .quantity(3)
                    .build();

            List<ProductPurchaseRequest> requests = List.of(request1, request2);

            when(productRepository.findAllByIdInOrderById(any())).thenReturn(List.of(existingProduct));

            assertThrows(ProductPurchaseException.class,
                    () -> productService.purchaseProducts(requests));
            verify(productRepository, times(1)).findAllByIdInOrderById(any());
            verify(productRepository, never()).save(any());
        }

        @Test
        @DisplayName("Should throw ProductPurchaseException when quantity is insufficient")
        void purchaseProducts_InsufficientQuantity_ThrowsException() {
            UUID productId = UUID.randomUUID();

            Product product = Product.builder()
                    .id(productId)
                    .name("Phone")
                    .description("Smartphone")
                    .availableQuantity(1.0)
                    .price(BigDecimal.valueOf(800))
                    .category(testProduct.getCategory())
                    .createdDate(LocalDate.now())
                    .build();

            ProductPurchaseRequest request = ProductPurchaseRequest.builder()
                    .productId(productId)
                    .quantity(5)
                    .build();

            when(productRepository.findAllByIdInOrderById(any())).thenReturn(List.of(product));

            assertThrows(ProductPurchaseException.class,
                    () -> productService.purchaseProducts(List.of(request)));
            verify(productRepository, times(1)).findAllByIdInOrderById(any());
            verify(productRepository, never()).save(any());
        }
    }

    @Nested
    class DeleteProductTests {

        @Test
        @DisplayName("Should call repository deleteById")
        void deleteProduct_ValidId_CallsRepository() {
            UUID productId = UUID.randomUUID();
            doNothing().when(productRepository).deleteById(productId);

            productService.deleteProduct(productId);

            verify(productRepository, times(1)).deleteById(productId);
        }
    }
}

