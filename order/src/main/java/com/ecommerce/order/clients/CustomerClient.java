package com.ecommerce.order.clients;

import com.ecommerce.order.clients.dtos.CustomerResponse;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

public interface CustomerClient {
    Optional<CustomerResponse> getCustomerById(@PathVariable UUID customer_id);
}
