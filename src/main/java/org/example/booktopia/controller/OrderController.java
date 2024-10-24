package org.example.booktopia.controller;


import lombok.RequiredArgsConstructor;
import org.example.booktopia.dtos.OrderDto;
import org.example.booktopia.mapper.OrderMapper;
import org.example.booktopia.model.Order;
import org.example.booktopia.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping("{buyerId}")
    public ResponseEntity<Iterable<OrderDto>> getOrdersByBuyerId(
            @PathVariable Long buyerId,
            @RequestParam(defaultValue = "0") Integer pageNumber,
            @RequestParam(defaultValue = "16") Integer pageSize) {
        Page<Order> orders = orderService.findAllByBuyerId(buyerId, pageNumber, pageSize);
        Iterable<OrderDto> orderDtos = orderMapper.toDto(orders.getContent());
        return ResponseEntity.ok(orderDtos);
    }
}
