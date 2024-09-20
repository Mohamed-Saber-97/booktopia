package repository;

import base.BaseRepository;
import jakarta.persistence.EntityManager;
import model.Category;

import java.util.List;

public class CategoryRepository extends BaseRepository<Category, Long> {
    public CategoryRepository() {
        super(Category.class);
    }

    public boolean existsByName(String name) {
        EntityManager entityManager = getEntityManager();
        boolean result = entityManager.createQuery("SELECT COUNT(c) FROM Category c WHERE c.name = :name", Long.class).setParameter("name", name).getSingleResult() > 0;
        closeEntityManager(entityManager);
        return result;
    }

    public Category findByName(String name) {
        EntityManager entityManager = getEntityManager();
        List<Category> result = findByField("name", name);
        closeEntityManager(entityManager);
        return result.isEmpty() ? null : result.getFirst();
    }
}
