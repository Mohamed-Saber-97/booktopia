package controller;

import model.Category;
import service.CategoryService;

import java.util.List;

public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController() {
        this.categoryService = new CategoryService();
    }

    public List<Category> findAll() {
        return categoryService.findAll();
    }

    public Category save(Category category) {
        return categoryService.save(category);
    }

    public Category findById(Long id) {
        return categoryService.findById(id);
    }

    public boolean existsByName(String name) {
        return categoryService.existsByName(name);
    }

    public Category update(Category category) {
        return categoryService.update(category);
    }
}
