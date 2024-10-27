package org.example.booktopia.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.dtos.OrderDto;
import org.example.booktopia.dtos.OrderProductDto;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.mapper.BuyerMapper;
import org.example.booktopia.mapper.OrderMapper;
import org.example.booktopia.mapper.OrderProductMapper;
import org.example.booktopia.mapper.ProductMapper;
import org.example.booktopia.model.*;
import org.example.booktopia.repository.OrderProductRepository;
import org.example.booktopia.repository.OrderRepository;
import org.example.booktopia.repository.ProductRepository;
import org.example.booktopia.specifications.OrderSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

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


    public List<OrderDto> searchOrders(Optional<Long> orderId, Optional<Long> buyerId, Integer pageNumber, Integer pageSize) {
        Specification<Order> specification = Specification.where(null);

        // Filter by Order ID if provided
        if (orderId.isPresent()) {
            specification = specification.and(OrderSpecifications.hasOrderId(orderId.get()));
        }

        // Filter by Customer ID if provided
        if (buyerId.isPresent()) {
            specification = specification.and(OrderSpecifications.hasBuyerId(buyerId.get()));
        }

        // Fetch paginated list of orders matching the specifications
        List<Order> orders = orderRepository.findAll(specification, PageRequest.of(pageNumber, pageSize)).getContent();

        // Convert to DTOs if necessary
        return orderMapper.toDto(orders); // Assuming you have an OrderMapper to map Order to OrderDto
    }




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
