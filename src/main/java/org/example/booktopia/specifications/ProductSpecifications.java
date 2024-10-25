package org.example.booktopia.specifications;

import org.example.booktopia.model.Product;
import org.springframework.data.jpa.domain.Specification;

import static org.example.booktopia.utils.RequestAttributeUtil.*;

public class ProductSpecifications {
    public static Specification<Product> hasName(String name) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(NAME), name));
    }

    public static Specification<Product> hasMinPrice(Integer minPrice) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(PRICE), minPrice));
    }

    public static Specification<Product> hasMaxPrice(Integer maxPrice) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(PRICE), maxPrice));
    }

    public static Specification<Product> hasCategory(Long categoryId) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(CATEGORY).get(ID), categoryId));
    }

    public static Specification<Product> isNotDeleted() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("isDeleted")));
    }
}
