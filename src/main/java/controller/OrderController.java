package controller;

import model.Order;
import model.OrderProduct;
import service.OrderService;

import java.util.List;

public class OrderController {
    private OrderService orderService;

    public OrderController() {
        orderService = new OrderService();
    }

    public Order findById(Long id) {
        return orderService.findById(id);
    }

    public List<OrderProduct> findProductsByBuyerId(Long buyerId, Long orderId) {
        return orderService.findProductsByBuyerId(buyerId, orderId);
    }

}
