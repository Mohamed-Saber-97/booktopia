package org.example.booktopia.controller;

import lombok.RequiredArgsConstructor;
import org.example.booktopia.dtos.CategoryDTO;
import org.example.booktopia.mapper.CategoryMapper;
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
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.findAllAvailableCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/available-categories")
    public ResponseEntity<List<Long>> getAvailableCategoriesById(@RequestBody List<Long> ids) {
        List<Long> categories = categoryService.findAllAvailableCategoriesById(ids);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.findById(id);
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDto) {
        Category category = CategoryMapper.INSTANCE.toEntity(categoryDto);
        CategoryDTO savedCategoryDto = categoryService.save(category);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedCategoryDto);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        CategoryDTO categoryDto = categoryService.findByName(name);
        return ResponseEntity.ok(categoryDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDto) {
        CategoryDTO updatedCategoryDto = categoryService.update(id, categoryDto);
        return ResponseEntity.ok(updatedCategoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
