package org.example.booktopia.repository;

import org.example.booktopia.model.BuyerInterest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BuyerInterestRepository extends JpaRepository<BuyerInterest, Long> {
    @Query("SELECT bi.id.categoryId FROM BuyerInterest bi where bi.id.buyerId = :buyerId and bi.category.isDeleted = false")
    List<Long> findCategoryIdsByBuyerId(@Param("buyerId") Long buyerId);
}
