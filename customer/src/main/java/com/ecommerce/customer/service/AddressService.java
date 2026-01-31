package com.ecommerce.customer.service;

import com.ecommerce.customer.dto.AddressResponseDto;
import com.ecommerce.customer.mapper.AddressMapper;
import com.ecommerce.customer.model.Address;
import com.ecommerce.customer.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AddressService {

    private AddressRepository addressRepository;


    public List<AddressResponseDto> getCustomerAddresses(UUID customerId) {
        List<Address> addresses = addressRepository.findByCustomer_Id(customerId);

        return addresses.stream()
                .map(AddressMapper::toDto).toList();
    }
}
