package org.example.booktopia.specifications;

import org.example.booktopia.model.Order;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecifications {
    public static Specification<Order> hasOrderId(Long orderId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), orderId);
    }

    public static Specification<Order> hasBuyerId(Long buyerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("buyer").get("id"), buyerId);
    }
}
