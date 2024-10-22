package org.example.booktopia.repository;

import org.example.booktopia.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Iterator;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("select c.id from Category c where c.id in :categoryIds and c.isDeleted = false")
    List<Long> findAllIdByAndIsDeletedIsFalse(@Param("categoryIds") List<Long> categoryIds);

    @Query("select count(c) > 0 from Category c where c.name = :name")
    Boolean existsByName(String name);

    @Query("select c from Category c where c.name = :name")
    Category findByName(String name);

    @Query("select c from Category c where c.isDeleted = false")
    List<Category> findAllAvailableCategories();
}
