package com.ecommerce.customer.integration;

import com.ecommerce.customer.dto.CustomerRequestDto;
import com.ecommerce.customer.dto.CustomerResponseDto;
import com.ecommerce.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class CustomerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void cleanUp() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Full Lifecycle: Create, Get, and Update Customer")
    void customerLifecycleIntegrationTest() throws Exception {
        // 1. Create a Customer
        CustomerRequestDto request = CustomerRequestDto.builder()
                .firstName("Integration")
                .lastName("User")
                .email("integration@test.com")
                .address("123 Tech Lane")
                .build();

        MvcResult createResult = mockMvc.perform(post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        CustomerResponseDto createdCustomer = objectMapper.readValue(
                createResult.getResponse().getContentAsString(),
                CustomerResponseDto.class
        );

        // 2. Verify existence in Database
        assertThat(customerRepository.existsByEmail("integration@test.com")).isTrue();

        // 3. Get the Customer by ID
        mockMvc.perform(get("/api/v1/customer/{id}", createdCustomer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Integration"))
                .andExpect(jsonPath("$.email").value("integration@test.com"));

        // 4. Update the Customer
        request.setFirstName("UpdatedName");
        mockMvc.perform(put("/api/v1/customer/{id}", createdCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("UpdatedName"));

        // 5. Delete the Customer
        mockMvc.perform(delete("/api/v1/customer/{id}", createdCustomer.getId()))
                .andExpect(status().isNoContent());

        // 6. Verify it's gone
        assertThat(customerRepository.existsByEmail("integration@test.com")).isFalse();
    }

    @Test
    @DisplayName("Should fail to create duplicate email across real database")
    void createDuplicateEmail_ReturnsConflict() throws Exception {
        // Save first user
        CustomerRequestDto request = CustomerRequestDto.builder()
                .firstName("First")
                .lastName("User")
                .email("duplicate@test.com")
                .address("Some Address")
                .build();

        mockMvc.perform(post("/api/v1/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // Try to save second user with same email
        mockMvc.perform(post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Validation: Should fail when email format is invalid")
    void createCustomer_InvalidEmail_ReturnsBadRequest() throws Exception {
        CustomerRequestDto request = CustomerRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("not-an-email")
                .address("123 Street")
                .build();

        mockMvc.perform(post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
