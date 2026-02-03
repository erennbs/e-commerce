package com.ecommerce.customer.mapper;

import com.ecommerce.customer.dto.CustomerRequestDto;
import com.ecommerce.customer.dto.CustomerResponseDto;
import com.ecommerce.customer.model.Customer;

public class CustomerMapper {
    public static CustomerResponseDto toDto(Customer customer) {
        return CustomerResponseDto.builder()
                .id(customer.getId().toString())
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .address(customer.getAddress())
                .build();
    }

    public static Customer toEntity(CustomerRequestDto customerRequestDto) {
        return Customer.builder()
                .firstName(customerRequestDto.getFirstName())
                .lastName(customerRequestDto.getLastName())
                .email(customerRequestDto.getEmail())
                .address(customerRequestDto.getAddress())
                .build();
    }
}
