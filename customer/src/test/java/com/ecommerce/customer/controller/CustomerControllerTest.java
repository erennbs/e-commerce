package com.ecommerce.customer.controller;

import com.ecommerce.customer.dto.CustomerRequestDto;
import com.ecommerce.customer.dto.CustomerResponseDto;
import com.ecommerce.customer.exception.CustomerNotFoundException;
import com.ecommerce.customer.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CustomerService customerService;

    private CustomerResponseDto responseDto;
    private CustomerRequestDto requestDto;
    private UUID customerId;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();

        responseDto = CustomerResponseDto.builder()
                .id(customerId.toString())
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .build();

        requestDto = CustomerRequestDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .address("123 Main St")
                .build();
    }

    @Test
    @DisplayName("GET /api/v1/customer - Should return list of customers")
    void getCustomers_ShouldReturnList() throws Exception {
        when(customerService.getCustomers()).thenReturn(List.of(responseDto));

        mockMvc.perform(get("/api/v1/customer"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @DisplayName("GET /api/v1/customer/{id} - Should return customer")
    void getCustomerById_ShouldReturnCustomer_ValidId() throws Exception {
        when(customerService.getCustomer(customerId)).thenReturn(responseDto);

        mockMvc.perform(get("/api/v1/customer/{id}", customerId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(customerId.toString()));
    }

    @Test
    @DisplayName("GET /api/v1/customer/{id} - Should return 404 when customer not found")
    void getCustomerById_InvalidId_ShouldReturnBadRequest() throws Exception {
        String errorMessage = "Customer not found with ID: " + customerId;
        when(customerService.getCustomer(customerId)).thenThrow(new CustomerNotFoundException(errorMessage));

        mockMvc.perform(get("/api/v1/customer/{id}",customerId))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Customer not found!"));
    }

    @Test
    @DisplayName("POST /api/v1/customer - Should create customer and return 200")
    void createCustomer_ValidRequest_ShouldReturnCustomer() throws Exception {
        when(customerService.createCustomer(any(CustomerRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    @DisplayName("POST /api/v1/customer - Should return 400 when validation fails")
    void createCustomer_InvalidRequest_ShouldReturnBadRequest() throws Exception {
        CustomerRequestDto invalidRequest = new CustomerRequestDto();

        mockMvc.perform(post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/v1/customer/{id} - Should update and return customer")
    void updateCustomer_ShouldReturnUpdatedCustomer() throws Exception {
        when(customerService.updateCustomer(eq(customerId), any(CustomerRequestDto.class)))
                .thenReturn(responseDto);

        mockMvc.perform(put("/api/v1/customer/{id}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId.toString()));
    }

    @Test
    @DisplayName("DELETE /api/v1/customer/{id} - Should return 204 No Content")
    void deleteCustomer_ShouldReturnNoContent() throws Exception {
        doNothing().when(customerService).deleteCustomer(customerId);

        mockMvc.perform(delete("/api/v1/customer/{id}", customerId))
                .andExpect(status().isNoContent());
    }


}