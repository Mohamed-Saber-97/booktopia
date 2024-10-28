package org.example.booktopia.repository;

import org.example.booktopia.model.OrderProduct;
import org.example.booktopia.model.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
    @Query("SELECT op from OrderProduct op where op.order.buyer.id = :buyerId and op.order.id = :orderId and op.isDeleted = false")
    List<OrderProduct> findAllProductsByBuyerIdAndOrderId(@Param("buyerId") Long buyerId,
                                                          @Param("orderId") Long orderId);
}
