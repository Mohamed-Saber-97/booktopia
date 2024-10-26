package org.example.booktopia.controller;

import org.example.booktopia.dtos.OrderDto;
import org.example.booktopia.dtos.OrderProductDto;
import org.example.booktopia.model.Order;
import org.example.booktopia.service.OrderService;
import org.example.booktopia.mapper.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetOrderById() {
        Long orderId = 1L;
        Order order = new Order();
        OrderDto orderDto = new OrderDto(orderId, "UNSHIPPED", 1L);

        when(orderService.findById(orderId)).thenReturn(order);
        when(orderMapper.toDto(order)).thenReturn(orderDto);

        ResponseEntity<OrderDto> response = orderController.getOrderById(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderDto, response.getBody());
    }

    @Test
    void testGetOrdersByBuyerId() {
        Long buyerId = 1L;
        int pageNumber = 0;
        int pageSize = 16;
        Order order = new Order();
        Page<Order> orderPage = new PageImpl<>(List.of(order));
        OrderDto orderDto = new OrderDto(1L, "UNSHIPPED", buyerId);

        when(orderService.findAllByBuyerId(buyerId, pageNumber, pageSize)).thenReturn(orderPage);
        when(orderMapper.toDto(orderPage.getContent())).thenReturn(List.of(orderDto));

        ResponseEntity<Iterable<OrderDto>> response = orderController.getOrdersByBuyerId(buyerId, pageNumber, pageSize);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(orderDto), response.getBody());
    }

    @Test
    void testCreateOrder() {
        Long buyerId = 1L;
        Map<Long, Integer> productQuantities = Map.of(1L, 2);
        OrderDto savedOrderDto = new OrderDto(1L, "UNSHIPPED", buyerId);

        when(orderService.save(buyerId, productQuantities)).thenReturn(savedOrderDto);

        ResponseEntity<OrderDto> response = orderController.createOrder(buyerId, productQuantities);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(savedOrderDto, response.getBody());
    }

    @Test
    void testGetProductsByOrderId() {
        Long orderId = 1L;
        OrderProductDto orderProductDto = new OrderProductDto(orderId, 1L, "Sample Book", "Description", "Author", "ISBN123", LocalDate.now(), 1L, "/image/path.jpg", BigDecimal.TEN, 2);

        when(orderService.findAllProductByOrderId(orderId)).thenReturn(List.of(orderProductDto));

        ResponseEntity<List<OrderProductDto>> response = orderController.getProductsByOrderId(orderId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(orderProductDto), response.getBody());
    }
}
