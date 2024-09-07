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

import static utils.RequestParameterUtil.*;

public class ProductRepository extends BaseRepository<Product, Long> {
    public ProductRepository() {
        super(Product.class);
    }

    public List<Product> search(Map<String, String> queryParameters, int pageNumber, int pageSize) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);
        Predicate predicate = cb.conjunction();
        if (queryParameters.getOrDefault(MINIMUM_PRICE, null) != null) {
            predicate = cb.and(predicate, cb.greaterThanOrEqualTo(productRoot.get(PRICE), new BigDecimal(queryParameters.get(MINIMUM_PRICE))));
        }
        if (queryParameters.getOrDefault(MAXIMUM_PRICE, null) != null) {
            predicate = cb.and(predicate, cb.lessThanOrEqualTo(productRoot.get(PRICE), BigDecimal.valueOf(Integer.parseInt(queryParameters.get(MAXIMUM_PRICE)))));
        }
        if (queryParameters.getOrDefault(CATEGORY, null) != null) {
            predicate = cb.and(predicate, cb.equal(productRoot.get(CATEGORY).get("id"), Long.parseLong(queryParameters.get(CATEGORY))));
        }
        if (queryParameters.getOrDefault(NAME, null) != null) {
            predicate = cb.and(predicate, cb.like(productRoot.get(NAME), "%" + queryParameters.get(NAME) + "%"));
        }
        query.select(productRoot).where(predicate);
        return entityManager.createQuery(query).setFirstResult(pageNumber * pageSize).setMaxResults(pageSize).getResultList();
//        return entityManager.createQuery(query).getResultList();
    }
}
