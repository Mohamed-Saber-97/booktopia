package org.example.booktopia.repository;

import org.example.booktopia.model.CartItem;
import org.example.booktopia.model.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
}
