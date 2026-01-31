package com.ecommerce.customer.mapper;

import com.ecommerce.customer.dto.AddressResponseDto;
import com.ecommerce.customer.model.Address;

public class AddressMapper {

    public static AddressResponseDto toDto(Address address) {
        return AddressResponseDto.builder()
                .id(address.getId().toString())
                .city(address.getCity())
                .street(address.getStreet())
                .houseNumber(address.getHouseNumber())
                .zipcode(address.getZipCode())
                .build();
    }

}
