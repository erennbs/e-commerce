package com.ecommerce.customer.controller;

import com.ecommerce.customer.dto.AddressResponseDto;
import com.ecommerce.customer.service.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/address")
@AllArgsConstructor
public class AddressController {

    private AddressService addressService;

    @GetMapping("/{customer_id}")
    public ResponseEntity<List<AddressResponseDto>> getCustomerAddresses(@PathVariable UUID customer_id) {
        List<AddressResponseDto> addresses = addressService.getCustomerAddresses(customer_id);
        return ResponseEntity.ok(addresses);
    }
}
