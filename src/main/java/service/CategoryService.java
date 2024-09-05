package service;

import model.Category;
import repository.CategoryRepository;

public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService() {
        categoryRepository = new CategoryRepository();
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }
}
