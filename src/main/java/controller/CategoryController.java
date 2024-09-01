package controller;

import model.Category;
import service.CategoryService;

public class CategoryController {
    private CategoryService categoryService;

    public CategoryController() {
        this.categoryService = new CategoryService();
    }

    public Category save(Category category) {
        return categoryService.save(category);
    }
}
