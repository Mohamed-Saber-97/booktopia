package org.example.booktopia.repository;

import jakarta.transaction.Transactional;
import jdk.dynalink.linker.LinkerServices;
import org.example.booktopia.model.CartItem;
import org.example.booktopia.model.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    @Modifying
    @Transactional
    @Query("DELETE FROM CartItem ci WHERE ci.buyer.id = :buyerId")
    void clearCartByBuyerId(Long buyerId);

    @Query("SELECT ci FROM CartItem ci WHERE ci.buyer.id = :buyerId")
    List<CartItem> findAllByBuyerId(Long buyerId);
}
