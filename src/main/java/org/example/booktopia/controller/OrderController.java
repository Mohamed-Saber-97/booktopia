package org.example.booktopia.controller;


import lombok.RequiredArgsConstructor;
import org.example.booktopia.dtos.OrderDto;
import org.example.booktopia.dtos.OrderProductDto;
import org.example.booktopia.mapper.OrderMapper;
import org.example.booktopia.model.Order;
import org.example.booktopia.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        OrderDto orderDto = orderMapper.toDto(order);
        return ResponseEntity.ok(orderDto);
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<Iterable<OrderDto>> getOrdersByBuyerId(
            @PathVariable Long buyerId,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "16") Integer pageSize) {
        Page<Order> orders = orderService.findAllByBuyerId(buyerId, pageNumber, pageSize);
        Iterable<OrderDto> orderDtos = orderMapper.toDto(orders.getContent());
        return ResponseEntity.ok(orderDtos);
    }

    @PostMapping("/buyer/{buyerId}")
    public ResponseEntity<OrderDto> createOrder(@PathVariable("buyerId") Long buyerId, @RequestBody Map<Long, Integer> productQuantities) {
        OrderDto savedOrderDto = orderService.save(buyerId, productQuantities);
        return ResponseEntity.ok(savedOrderDto);
    }

    @GetMapping("/{orderId}/products")
    public ResponseEntity<List<OrderProductDto>> getProductsByOrderId(@PathVariable Long orderId) {
        List<OrderProductDto> orderProductDtos = orderService.findAllProductByOrderId(orderId);
        return ResponseEntity.ok(orderProductDtos);
    }
}
