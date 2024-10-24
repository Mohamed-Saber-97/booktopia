package org.example.booktopia.controller;

import lombok.RequiredArgsConstructor;
import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.model.Category;
import org.example.booktopia.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

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
        CategoryDto category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategoryDto = categoryService.save(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedCategoryDto);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDto> getCategoryByName(@PathVariable String name) {
        CategoryDto categoryDto = categoryService.findByName(name);
        return ResponseEntity.ok(categoryDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategoryDto = categoryService.update(id, categoryDto);
        return ResponseEntity.ok(updatedCategoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDto> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
