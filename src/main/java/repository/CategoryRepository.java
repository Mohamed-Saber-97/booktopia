package repository;

import base.BaseRepository;
import model.Category;

public class CategoryRepository extends BaseRepository<Category, Long> {
    public CategoryRepository() {
        super(Category.class);
    }

    public boolean existsByName(String name) {
        return entityManager.createQuery("SELECT COUNT(c) FROM Category c WHERE c.name = :name", Long.class).setParameter("name", name).getSingleResult() > 0;
    }

    public Category findByName(String name) {
        Category category = null;
        try {
            category = entityManager.createQuery("SELECT c FROM Category c WHERE c.name = :name", Category.class).setParameter("name", name).getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return category;
    }
}
