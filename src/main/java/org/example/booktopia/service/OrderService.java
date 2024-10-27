package org.example.booktopia.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.dtos.OrderDto;
import org.example.booktopia.dtos.OrderProductDto;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.mapper.OrderMapper;
import org.example.booktopia.mapper.OrderProductMapper;
import org.example.booktopia.mapper.ProductMapper;
import org.example.booktopia.model.*;
import org.example.booktopia.repository.OrderRepository;
import org.example.booktopia.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final BuyerService buyerService;
    private final OrderMapper orderMapper;
    private final OrderProductMapper orderProductMapper;
    private final ProductMapper productMapper;

    public Page<Order> findAllByBuyerId(Long buyerId, int pageNumber, int pageSize) {
        buyerService.findById(buyerId);
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return orderRepository.findAllByBuyerIdAndIsDeletedIsFalse(buyerId, pageRequest);
    }

    public List<OrderProductDto> findAllProductByOrderId(Long id) {
        Order order = orderRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new RecordNotFoundException("Order", "ID", id.toString()));
        return order.getOrderProducts().stream()
                .map(orderProductMapper::toDto)
                .toList();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Order", "ID", id.toString()));
    }

    @Transactional
    public OrderDto save(Long buyerId, Map<Long, Integer> productQuantities) {
        Order order = new Order();
        Buyer buyer = buyerService.findById(buyerId);
        order.setBuyer(buyer);
        order.setStatus(Order.OrderStatus.UNSHIPPED);
        order = orderRepository.save(order);

        for (Map.Entry<Long, Integer> entry : productQuantities.entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product existingProduct = productRepository.findById(productId)
                    .orElseThrow(() -> new RecordNotFoundException("Product", "ID", productId.toString()));

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(existingProduct);
            orderProduct.setPrice(existingProduct.getPrice());
            orderProduct.setQuantity(quantity);
            OrderProductId orderProductId = new OrderProductId();
            orderProductId.setOrderId(order.getId());
            orderProductId.setProductId(existingProduct.getId());
            orderProduct.setId(orderProductId);
            order.getOrderProducts().add(orderProduct);
        }
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }
}
