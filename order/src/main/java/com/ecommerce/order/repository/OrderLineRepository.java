package com.ecommerce.order.repository;

import com.ecommerce.order.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLine, UUID> {
}
