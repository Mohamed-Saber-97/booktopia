package org.example.booktopia.repository;

import org.example.booktopia.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p where p.category.id IN :categoryIds and p.isDeleted = false ")
    List<Product> findAllByCategoryIds(@Param("categoryIds") List<Long> categoryIds);
    @Query ("SELECT COUNT(p) FROM Product p WHERE p.isbn = :isbn")
    int existByIsbn(String isbn);
}
