package org.example.booktopia.repository;

import jakarta.transaction.Transactional;
import org.example.booktopia.model.BuyerWishlist;
import org.example.booktopia.model.BuyerWishlistId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuyerWishlistRepository extends JpaRepository<BuyerWishlist, BuyerWishlistId> {
    @Modifying
    @Transactional
    @Query("DELETE FROM BuyerWishlist bw WHERE bw.id.buyerId = :buyerId AND bw.id.productId = :productId")
    void removeProductFromWishlist(Long buyerId, Long productId);

    boolean existsByBuyerIdAndProductId(Long buyerId, Long productId);

    List<BuyerWishlist> findByBuyer_id(Long buyerId);
}
