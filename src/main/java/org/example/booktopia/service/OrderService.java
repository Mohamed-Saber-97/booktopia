package org.example.booktopia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.model.Order;
import org.example.booktopia.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;

    public Page<Order> findAllByCustomerId(Long buyerId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findAllByBuyerIdAndIsDeletedIsFalse(buyerId, pageRequest);
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                              .orElseThrow(() -> new RecordNotFoundException("Order", "ID", id.toString()));
    }
}
