package com.ecommerce.customer.repository;

import com.ecommerce.customer.model.Customer;
import com.ecommerce.customer.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void existsByEmail_shouldReturnTrue_WhenEmailExist() {
        Customer customer = Customer.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .address("456 Park Ave")
                .createdDate(LocalDate.now())
                .build();
        customerRepository.save(customer);

        boolean exists = customerRepository.existsByEmail("jane@example.com");

        assertTrue(exists);
    }

    @Test
    void existsByEmail_shouldReturnTrue_WhenEmailNotExist() {
        Customer customer = Customer.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .address("456 Park Ave")
                .createdDate(LocalDate.now())
                .build();
        customerRepository.save(customer);

        boolean exists = customerRepository.existsByEmail("john@example.com");

        assertFalse(exists);
    }

    @Test
    void existsByEmailAndIdNot_ShouldReturnFalse_WhenOtherCustomersNotHaveSameEmail() {
        Customer customer = Customer.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .address("456 Park Ave")
                .createdDate(LocalDate.now())
                .build();
        Customer saved = customerRepository.save(customer);

        boolean result = customerRepository.existsByEmailAndIdNot("jane@example.com", saved.getId());

        assertFalse(result);
    }

    @Test
    void existsByEmailAndIdNot_ShouldReturnTrue_WhenOtherCustomersHaveSameEmail() {
        Customer customer = Customer.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .address("456 Park Ave")
                .createdDate(LocalDate.now())
                .build();
        customerRepository.save(customer);

        boolean result = customerRepository.existsByEmailAndIdNot("jane@example.com", UUID.randomUUID());

        assertTrue(result);
    }
}