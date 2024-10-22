package org.example.booktopia.controller;

import org.example.booktopia.converters.CategoryDtoToCategoryConverter;
import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.error.ErrorMessage;
import org.example.booktopia.model.Category;
import org.example.booktopia.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
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
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        CategoryDto category = categoryService.findById(id);
        if (category == null) {
            ErrorMessage errorMessage = new ErrorMessage("Category with id " + id + " not found", 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto) {
        Category category = CategoryDtoToCategoryConverter.convert(categoryDto);
        CategoryDto savedCategoryDto = categoryService.save(category);
        if (savedCategoryDto == null) {
            ErrorMessage errorMessage = new ErrorMessage("Category with name " + categoryDto.name() + " already exists", 409);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
        }
        return ResponseEntity.ok(savedCategoryDto);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name) {
        CategoryDto categoryDto = categoryService.findByName(name);
        if (categoryDto == null) {
            ErrorMessage errorMessage = new ErrorMessage("Category with name " + name + " not found", 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
        return ResponseEntity.ok(categoryDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        Category category = CategoryDtoToCategoryConverter.convert(categoryDto);
        category.setId(id);

        CategoryDto updatedCategoryDto = categoryService.update(category);
        if (updatedCategoryDto == null) {
            ErrorMessage errorMessage = new ErrorMessage("Category with id " + id + " not found", 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
        return ResponseEntity.ok(updatedCategoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        CategoryDto categoryDto = categoryService.findById(id);
        if (categoryDto == null) {
            ErrorMessage errorMessage = new ErrorMessage("Category with id " + id + " not found", 404);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
        categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
