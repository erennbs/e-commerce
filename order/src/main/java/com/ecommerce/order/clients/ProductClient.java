package com.ecommerce.order.clients;

import com.ecommerce.order.clients.dtos.PurchaseRequest;
import com.ecommerce.order.clients.dtos.PurchaseResponse;

import java.util.List;

public interface ProductClient {
    List<PurchaseResponse> purchaseProducts(List<PurchaseRequest> purchaseRequest);
}
