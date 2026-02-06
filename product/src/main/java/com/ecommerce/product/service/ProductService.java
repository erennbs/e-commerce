package com.ecommerce.product.service;

import com.ecommerce.product.dto.ProductPurchaseRequest;
import com.ecommerce.product.dto.ProductPurchaseResponse;
import com.ecommerce.product.dto.ProductRequestDto;
import com.ecommerce.product.dto.ProductResponseDto;
import com.ecommerce.product.exception.ProductNotFoundException;
import com.ecommerce.product.exception.ProductPurchaseException;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.model.Product;
import com.ecommerce.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDto).toList();
    }

    public ProductResponseDto getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with Id: " + id)
        );

        return ProductMapper.toDto(product);
    }

    public ProductResponseDto createProduct(ProductRequestDto productRequest) {
        Product product = ProductMapper.toEntity(productRequest);

        return ProductMapper.toDto(productRepository.save(product));
    }

    @Transactional
    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> productPurchaseRequest) {
        List<UUID> productIds = productPurchaseRequest.stream()
                .map(ProductPurchaseRequest::getProductId)
                .toList();

        List<Product> storedProducts = productRepository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exists");
        }

        List<ProductPurchaseRequest> requestSorted =  productPurchaseRequest.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::getProductId))
                .toList();

        ArrayList<ProductPurchaseResponse> purchasedProducts = new ArrayList<ProductPurchaseResponse>();

        for (int i = 0; i < requestSorted.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = requestSorted.get(i);

            if (product.getAvailableQuantity() < productRequest.getQuantity()) {
                throw new ProductPurchaseException("Insufficient quantity for product with Id: " + product.getId());
            }
            product.setAvailableQuantity(product.getAvailableQuantity() - productRequest.getQuantity());
            productRepository.save(product);
            purchasedProducts.add(ProductMapper.toPurchaseResponse(product));
        }

        return purchasedProducts;
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
