package org.example.booktopia.repository;

import org.example.booktopia.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findAllByBuyerIdAndIsDeletedIsFalse(Long buyerId, Pageable pageable);
}
