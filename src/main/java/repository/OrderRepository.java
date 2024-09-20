package repository;

import base.BaseRepository;
import jakarta.persistence.EntityManager;
import model.Order;
import model.OrderProduct;

import java.util.List;

public class OrderRepository extends BaseRepository<Order, Long> {
    public OrderRepository() {
        super(Order.class);
    }

    public List<OrderProduct> findProductsByBuyerId(Long buyerId, Long orderId) {
        EntityManager entityManager = getEntityManager();
        List<OrderProduct> result = entityManager.createQuery("SELECT op FROM OrderProduct op WHERE op.order.buyer.id = :buyerId AND op.order.id = :orderId AND op.isDeleted = false", OrderProduct.class)
                .setParameter("buyerId", buyerId)
                .setParameter("orderId", orderId)
                .getResultList();
        closeEntityManager(entityManager);
        return result;
    }
}
