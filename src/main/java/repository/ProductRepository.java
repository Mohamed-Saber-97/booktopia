package repository;

import base.BaseRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class ProductRepository extends BaseRepository<Product, Long> {
    public ProductRepository() {
        super(Product.class);
    }

    public List<Product> search(Map<String, String> queryParameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);
        Predicate predicate = cb.conjunction();
        if (queryParameters.getOrDefault("minPrice", null) != null) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(productRoot.get("price"), new BigDecimal(queryParameters.get("minPrice"))));
        }
        if (queryParameters.getOrDefault("maxPrice", null) != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(productRoot.get("price"), BigDecimal.valueOf(Integer.parseInt(queryParameters.get("maxPrice")))));
        }
        if (queryParameters.getOrDefault("category", null) != null) {
            predicate = cb.and(predicate, cb.equal(productRoot.get("category").get("id"), Long.parseLong(queryParameters.get("category"))));
        }
        query.select(productRoot).where(predicate);
        return entityManager.createQuery(query).getResultList();
    }
}
