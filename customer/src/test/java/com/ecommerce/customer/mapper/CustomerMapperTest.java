package com.ecommerce.customer.mapper;

import com.ecommerce.customer.dto.CustomerRequestDto;
import com.ecommerce.customer.dto.CustomerResponseDto;
import com.ecommerce.customer.model.Customer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    @Test
    @DisplayName("Should map Customer Entity to CustomerResponseDto correctly")
    void toDto_ShouldMapAllFields() {
        UUID id = UUID.randomUUID();
        Customer customer = Customer.builder()
                .id(id)
                .firstName("Alice")
                .lastName("Smith")
                .email("alice@example.com")
                .address("789 Maple Ave")
                .createdDate(LocalDate.now())
                .build();

        CustomerResponseDto dto = CustomerMapper.toDto(customer);

        assertNotNull(dto);
        assertEquals(id.toString(), dto.getId());
        assertEquals(customer.getFirstName(), dto.getFirstName());
        assertEquals(customer.getLastName(), dto.getLastName());
        assertEquals(customer.getEmail(), dto.getEmail());
        assertEquals(customer.getAddress(), dto.getAddress());
    }

    @Test
    @DisplayName("Should map CustomerRequestDto to Customer Entity correctly")
    void toEntity_ShouldMapAllFields() {
        CustomerRequestDto requestDto = CustomerRequestDto.builder()
                .firstName("Bob")
                .lastName("Jones")
                .email("bob@example.com")
                .address("321 Oak St")
                .build();

        Customer entity = CustomerMapper.toEntity(requestDto);

        assertNotNull(entity);
        assertNull(entity.getId(), "ID should be null before persistence");
        assertEquals(requestDto.getFirstName(), entity.getFirstName());
        assertEquals(requestDto.getLastName(), entity.getLastName());
        assertEquals(requestDto.getEmail(), entity.getEmail());
        assertEquals(requestDto.getAddress(), entity.getAddress());
    }
}