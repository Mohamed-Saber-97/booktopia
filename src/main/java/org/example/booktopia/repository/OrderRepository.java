package org.example.booktopia.repository;

import org.example.booktopia.model.Order;
import org.example.booktopia.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    Page<Order> findAllByBuyerIdAndIsDeletedIsFalse(Long buyerId, Pageable pageable);

    Optional<Order> findByIdAndIsDeletedIsFalse(Long id);
}
