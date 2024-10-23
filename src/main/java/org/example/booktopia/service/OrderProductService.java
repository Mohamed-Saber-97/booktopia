package org.example.booktopia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.model.OrderProduct;
import org.example.booktopia.repository.OrderProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProductService {
    private final OrderProductRepository orderProductRepository;

    public List<OrderProduct> findAllProductsByBuyerIdAndOrderId(Long buyerId, Long orderId) {
        return orderProductRepository.findAllProductsByBuyerIdAndOrderId(buyerId, orderId);
    }
}
