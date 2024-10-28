package org.example.booktopia.repository;

import org.example.booktopia.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("SELECT p FROM Product p where p.category.id IN :categoryIds and p.isDeleted = false and p.category.isDeleted = false")
    List<Product> findAllByCategoryIds(@Param("categoryIds") Iterable<Long> categoryIds);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false")
    List<Product> findAll();

    @Query(value = "SELECT p.* from product p where p.is_deleted = false LIMIT ?1", nativeQuery = true)
    List<Product> findFirst(Integer x);

    @Query("SELECT p FROM Product p WHERE p.isDeleted = false and p.quantity > 0")
    List<Product> findAllAvailableProducts();

    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.isDeleted = false")
    Optional<Product> findById(@Param("id") Long id);

    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.isbn = :isbn")
    Boolean existsByIsbn(@Param("isbn") String isbn);

    @Query("SELECT p FROM Product p WHERE p.isbn = :isbn AND p.isDeleted = false")
    Optional<Product> findByIsbn(@Param("isbn") String isbn);

    @Query("SELECT p FROM Product p WHERE p.name = :name AND p.isDeleted = false")
    Optional<Product> findByName(@Param("name") String name);

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
}
