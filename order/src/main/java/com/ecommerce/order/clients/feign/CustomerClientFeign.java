package com.ecommerce.order.clients.feign;

import com.ecommerce.order.clients.CustomerClient;
import com.ecommerce.order.clients.dtos.CustomerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "customer-service", url = "${application.config.customer-url}")
public interface CustomerClientFeign extends CustomerClient {

    @GetMapping("/{customer_id}")
    Optional<CustomerResponse> getCustomerById(@PathVariable UUID customer_id);

}
