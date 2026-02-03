package com.ecommerce.customer.service;

import com.ecommerce.customer.dto.CustomerRequestDto;
import com.ecommerce.customer.dto.CustomerResponseDto;
import com.ecommerce.customer.exception.CustomerNotFoundException;
import com.ecommerce.customer.exception.EmailAlreadyExistsException;
import com.ecommerce.customer.model.Customer;
import com.ecommerce.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CustomerServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;
    private CustomerResponseDto testCustomerResponseDto;
    private CustomerRequestDto testCustomerRequestDto;

    @BeforeEach
    void setUp() {
        testCustomerRequestDto = CustomerRequestDto.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@mail.com")
                .address("Test Address")
                .build();

        testCustomer = Customer.builder()
                .id(UUID.fromString("4d5ec895-dd60-42e6-b761-05d09dd9229f"))
                .firstName("Test")
                .lastName("User")
                .email("test@mail.com")
                .address("Test Address")
                .createdDate(LocalDate.now())
                .build();

        testCustomerResponseDto = CustomerResponseDto.builder()
                .firstName("Test")
                .lastName("User")
                .email("test@mail.com")
                .adress("Test Address")
                .build();
    }

    @Nested
    class GetCustomersTests {
        @Test
        @DisplayName("Should return a list of CustomerResponseDTOs")
        void getCustomers_ReturnList() {
            when(customerRepository.findAll()).thenReturn(List.of(testCustomer));

            List<CustomerResponseDto> result = customerService.getCustomers();

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(testCustomer.getEmail(), result.get(0).getEmail());
            verify(customerRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Should return a single CustomerResponseDTO when ID exists")
        void getCustomer_ValidId_ReturnsDto() {
            when(customerRepository.findById(testCustomer.getId())).thenReturn(Optional.of(testCustomer));

            CustomerResponseDto result = customerService.getCustomer(testCustomer.getId());

            assertNotNull(result);
            assertEquals(testCustomer.getEmail(), result.getEmail());
            verify(customerRepository).findById(testCustomer.getId());
        }

        @Test
        @DisplayName("Should throw CustomerNotFoundException when ID does not exist")
        void getCustomer_InvalidId_ThrowsException() {
            when(customerRepository.findById(testCustomer.getId())).thenReturn(Optional.empty());

            assertThrows(CustomerNotFoundException.class, () -> customerService.getCustomer(testCustomer.getId()));
        }
    }

    @Nested
    class CreateCustomerTests {

        @Test
        @DisplayName("Should create customer successfully with valid request and email")
        void createCustomer_ValidRequest_ReturnsDto() {
            when(customerRepository.existsByEmail(testCustomerRequestDto.getEmail())).thenReturn(false);
            when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

            CustomerResponseDto result = customerService.createCustomer(testCustomerRequestDto);

            assertNotNull(result);
            verify(customerRepository).save(any(Customer.class));
        }

        @Test
        @DisplayName("Should throw EmailAlreadyExistsException when email is taken")
        void createCustomer_DuplicateEmail_ThrowsException() {
            when(customerRepository.existsByEmail(testCustomerRequestDto.getEmail())).thenReturn(true);

            assertThrows(EmailAlreadyExistsException.class, () -> customerService.createCustomer(testCustomerRequestDto));
            verify(customerRepository, never()).save(any());
        }
    }

    @Nested
    class UpdateCustomerTests {
        @Test
        @DisplayName("Should update customer successfully")
        void updateCustomer_ValidRequest_ReturnsDto() {
            when(customerRepository.findById(testCustomer.getId())).thenReturn(Optional.of(testCustomer));
            when(customerRepository.existsByEmailAndIdNot(testCustomerRequestDto.getEmail(), testCustomer.getId())).thenReturn(false);
            when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

            CustomerResponseDto result = customerService.updateCustomer(testCustomer.getId(), testCustomerRequestDto);

            assertNotNull(result);
            verify(customerRepository, times(1)).save(any(Customer.class));

        }

        @Test
        @DisplayName("Should throw EmailAlreadyExistsException if new email is used by another customer")
        void updateCustomer_DuplicateEmail_ThrowsException() {
            when(customerRepository.findById(testCustomer.getId())).thenReturn(Optional.of(testCustomer));
            when(customerRepository.existsByEmailAndIdNot(testCustomerRequestDto.getEmail(), testCustomer.getId())).thenReturn(true);

            assertThrows(EmailAlreadyExistsException.class,
                    () -> customerService.updateCustomer(testCustomer.getId(), testCustomerRequestDto));
            verify(customerRepository, never()).save(any());

        }

        @Test
        @DisplayName("Should throw CustomerNotFoundException if customer with Id doesn't exists")
        void updateCustomer_InvalidId_ThrowsException() {
            when(customerRepository.findById(testCustomer.getId())).thenReturn(Optional.empty());

            assertThrows(CustomerNotFoundException.class,
                    () -> customerService.updateCustomer(testCustomer.getId(), testCustomerRequestDto));
            verify(customerRepository, never()).save(any());
        }
    }

    @Nested
    class DeleteCustomerTests {
        @Test
        @DisplayName("Should call repository deleteById")
        void deleteCustomer_ValidId_ReturnsDto() {
            doNothing().when(customerRepository).deleteById(testCustomer.getId());

            customerService.deleteCustomer(testCustomer.getId());

            verify(customerRepository, times(1)).deleteById(testCustomer.getId());
        }
    }
}