package com.ecommerce.customer.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponseDto {
    private String id;
    private String city;
    private String street;
    private String houseNumber;
    private String zipcode;
}
