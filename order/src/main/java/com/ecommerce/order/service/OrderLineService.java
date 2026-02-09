package com.ecommerce.order.service;

import com.ecommerce.order.dto.OrderLineRequest;
import com.ecommerce.order.dto.OrderLineResponse;
import com.ecommerce.order.mapper.OrderLineMapper;
import com.ecommerce.order.model.OrderLine;
import com.ecommerce.order.repository.OrderLineRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderLineService {
    private final OrderLineRepository orderLineRepository;

    public OrderLineResponse createOrderLine(OrderLineRequest orderLineRequest) {
        OrderLine orderLine = orderLineRepository.save(OrderLineMapper.toEntity(orderLineRequest));

        return OrderLineMapper.toDto(orderLine);
    }
}


