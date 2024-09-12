package service;

import model.Order;
import model.OrderProduct;
import repository.OrderRepository;

import java.util.List;

public class OrderService {
    private OrderRepository orderRepository;

    public OrderService() {
        orderRepository = new OrderRepository();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<OrderProduct> findProductsByBuyerId(Long buyerId, Long orderId) {
        return orderRepository.findProductsByBuyerId(buyerId, orderId);
    }
}
