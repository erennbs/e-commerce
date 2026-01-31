package com.ecommerce.customer.repository;

import com.ecommerce.customer.model.Address;
import com.ecommerce.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    List<Address> findByCustomer_Id(UUID customerId);
}
