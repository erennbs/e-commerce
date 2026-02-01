package com.ecommerce.customer.mapper;

import com.ecommerce.customer.dto.CustomerRequestDto;
import com.ecommerce.customer.dto.CustomerResponseDto;
import com.ecommerce.customer.model.Customer;

import java.time.LocalDate;
import java.util.List;

public class CustomerMapper {
    public static CustomerResponseDto toDto(Customer customer) {
        return CustomerResponseDto.builder()
                .id(customer.getId().toString())
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .adress(
                        customer.getAdress() == null
                        ? List.of()
                        : customer.getAdress().stream().map(AddressMapper::toDto).toList()
                )
                .build();
    }

    public static Customer toEntity(CustomerRequestDto customerRequestDto) {
        return Customer.builder()
                .firstName(customerRequestDto.getFirstName())
                .lastName(customerRequestDto.getLastName())
                .email(customerRequestDto.getEmail())
                .build();
    }
}
