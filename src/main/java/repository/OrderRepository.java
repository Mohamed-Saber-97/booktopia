package repository;

import base.BaseRepository;
import model.Order;
import model.OrderProduct;
import model.Product;

import java.util.List;

public class OrderRepository extends BaseRepository<Order, Long> {
    public OrderRepository() {
        super(Order.class);
    }

    public List<OrderProduct> findProductsByBuyerId(Long buyerId, Long orderId) {
        return entityManager.createQuery("SELECT op FROM OrderProduct op WHERE op.order.buyer.id = :buyerId AND op.order.id = :orderId AND op.isDeleted = false", OrderProduct.class)
                .setParameter("buyerId", buyerId)
                .setParameter("orderId", orderId)
                .getResultList();
    }
}
