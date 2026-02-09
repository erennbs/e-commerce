package com.ecommerce.order.clients.rest;

import com.ecommerce.order.clients.ProductClient;
import com.ecommerce.order.exception.OrderException;
import com.ecommerce.order.clients.dtos.PurchaseRequest;
import com.ecommerce.order.clients.dtos.PurchaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductClientRest implements ProductClient {

    @Value("${application.config.product-url}")
    private String productUrl;

    private final RestTemplate restTemplate;

    public List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> purchaseRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<List<PurchaseRequest>> entity = new HttpEntity<>(purchaseRequest, headers);

        ParameterizedTypeReference<List<PurchaseResponse>> responseType =
                new ParameterizedTypeReference<>() {};

        ResponseEntity<List<PurchaseResponse>> response = restTemplate.exchange(
                productUrl + "/purchase",
                HttpMethod.POST,
                entity,
                responseType
        );

        if  (response.getStatusCode().isError()) {
            throw new OrderException("An error occurred while processing the order");
        }

        return response.getBody();
    }
}
