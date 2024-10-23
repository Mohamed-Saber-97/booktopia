package org.example.booktopia.controller;

import org.example.booktopia.converters.CategoryDtoToCategoryConverter;
import org.example.booktopia.converters.CategoryToCategoryDtoConverter;
import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.model.Category;
import org.example.booktopia.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryService.findAllAvailableCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/available-categories")
    public ResponseEntity<List<Long>> getAvailableCategoriesById(@RequestBody List<Long> ids) {
        List<Long> categories = categoryService.findAllAvailableCategoriesById(ids);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        CategoryDto categoryDto = CategoryToCategoryDtoConverter.convert(category);
        return ResponseEntity.ok(categoryDto);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto) {
        Category category = CategoryDtoToCategoryConverter.convert(categoryDto);
        Category savedCategory = categoryService.save(category);
        CategoryDto savedCategoryDto = CategoryToCategoryDtoConverter.convert(savedCategory);
        return ResponseEntity.ok(savedCategoryDto);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDto> getCategoryByName(@PathVariable String name) {
        Category category = categoryService.findByName(name);
        CategoryDto categoryDto = CategoryToCategoryDtoConverter.convert(category);
        return ResponseEntity.ok(categoryDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        Category category = categoryService.findById(id);

        if (categoryDto.name() != null) {
            category.setName(categoryDto.name());
        }
        Category updatedCategory = categoryService.save(category);
        CategoryDto updatedCategoryDto = CategoryToCategoryDtoConverter.convert(updatedCategory);
        return ResponseEntity.ok(updatedCategoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
