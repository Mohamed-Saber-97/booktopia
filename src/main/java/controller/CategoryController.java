package controller;

import model.Category;
import service.CategoryService;

import java.util.List;

public class CategoryController {
    private CategoryService categoryService;

    public CategoryController() {
        this.categoryService = new CategoryService();
    }

    public List<Category> findAll() {
        return categoryService.findAll();
    }

    public Category save(Category category) {
        return categoryService.save(category);
    }
}
