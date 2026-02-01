package com.ecommerce.customer.service;

import com.ecommerce.customer.dto.CustomerRequestDto;
import com.ecommerce.customer.dto.CustomerResponseDto;
import com.ecommerce.customer.exception.CustomerNotFoundException;
import com.ecommerce.customer.exception.EmailAlreadyExistsException;
import com.ecommerce.customer.mapper.CustomerMapper;
import com.ecommerce.customer.model.Customer;
import com.ecommerce.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerService {
    private CustomerRepository customerRepository;

    public List<CustomerResponseDto> getCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream()
                .map(CustomerMapper::toDto)
                .toList();
    }

    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto) {
        if (customerRepository.existsByEmail(customerRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException("A patient with this email already exists: "
                    + customerRequestDto.getEmail());
        }
        Customer customer = CustomerMapper.toEntity(customerRequestDto);
        customer.setCreatedDate(LocalDate.now());
        Customer newCustomer = customerRepository.save(customer);

        return CustomerMapper.toDto(newCustomer);
    }

    public CustomerResponseDto updateCustomer(UUID id, CustomerRequestDto customerRequestDto) {
        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Customer not found with ID: " + id));

        if (customerRepository.existsByEmailAndIdNot(customerRequestDto.getEmail(), id)) {
            throw new EmailAlreadyExistsException("A patient with this email already exists: "
                    + customerRequestDto.getEmail());
        }

        customer.setEmail(customerRequestDto.getEmail());
        customer.setFirstName(customerRequestDto.getFirstName());
        customer.setLastName(customerRequestDto.getLastName());
        customer.setAddress(customerRequestDto.getAddress());

        Customer updatedCustomer = customerRepository.save(customer);

        return  CustomerMapper.toDto(updatedCustomer);
    }
}
