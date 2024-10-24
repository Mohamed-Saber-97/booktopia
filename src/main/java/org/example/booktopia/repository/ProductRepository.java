package org.example.booktopia.repository;

import org.example.booktopia.model.Category;
import org.example.booktopia.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p where p.category.id IN :categoryIds and p.isDeleted = false ")
    List<Product> findAllByCategoryIds(@Param("categoryIds") List<Long> categoryIds);

    @Query("select p from Product p where p.name = :name")
    Optional<Product> findByName(String name);

    @Query("select count(p) > 0 from Product p where p.name = :name")
    Boolean existsByName(String name);

    Boolean existsByNameAndIdNotAndIsDeletedIsFalse(String name, Long id);


}
