package com.ecommerce.order.clients.feign;

import com.ecommerce.order.clients.ProductClient;
import com.ecommerce.order.clients.dtos.PurchaseRequest;
import com.ecommerce.order.clients.dtos.PurchaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE", path = "/api/v1/products")
public interface ProductClientFeign extends ProductClient {

    @PostMapping("/purchase")
    List<PurchaseResponse> purchaseProducts(@RequestBody List<PurchaseRequest> purchaseRequest);
}
