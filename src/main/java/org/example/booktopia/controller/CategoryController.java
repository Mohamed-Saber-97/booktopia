package org.example.booktopia.controller;

import org.example.booktopia.dtos.CategoryDTO;
import org.example.booktopia.error.ErrorMessage;
import org.example.booktopia.mapper.CategoryMapper;
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
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        CategoryDTO category = categoryService.findById(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(new ErrorMessage("Category with id " + id + " not found", HttpStatus.NOT_FOUND));
        }
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDTO categoryDto) {
        Category category = CategoryMapper.INSTANCE.toEntity(categoryDto);
        CategoryDTO savedCategoryDto = categoryService.save(category);
        if (savedCategoryDto == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body(new ErrorMessage("Category with name " + categoryDto.name() + " already exists",
                                                        HttpStatus.CONFLICT));
        }
        return ResponseEntity.ok(savedCategoryDto);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name) {
        CategoryDTO categoryDto = categoryService.findByName(name);
        if (categoryDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(new ErrorMessage("Category with name " + name + " not found",
                                                        HttpStatus.NOT_FOUND));
        }
        return ResponseEntity.ok(categoryDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDto) {
        Category category = CategoryMapper.INSTANCE.toEntity(categoryDto);
        category.setId(id);

        CategoryDTO updatedCategoryDto = categoryService.update(category);
        if (updatedCategoryDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(new ErrorMessage("Category with id " + id + " not found", HttpStatus.NOT_FOUND));
        }
        return ResponseEntity.ok(updatedCategoryDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        CategoryDTO categoryDto = categoryService.findById(id);
        if (categoryDto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(new ErrorMessage("Category with id " + id + " not found", HttpStatus.NOT_FOUND));
        }
        categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                             .build();
    }
}
