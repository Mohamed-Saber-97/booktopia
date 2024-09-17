package service;

import model.Category;
import repository.CategoryRepository;

import java.util.List;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService() {
        categoryRepository = new CategoryRepository();
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public Category update(Category category) {
        return categoryRepository.update(category);
    }
    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
