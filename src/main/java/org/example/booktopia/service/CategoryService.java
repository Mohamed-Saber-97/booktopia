package org.example.booktopia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.converters.CategoryToCategoryDtoConverter;
import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.model.Category;
import org.example.booktopia.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Long> findAllAvailableCategoriesById(List<Long> ids) {
        return categoryRepository.findAllIdByAndIsDeletedIsFalse(ids);
    }

    public List<CategoryDto> findAllAvailableCategories() {
        List<Category> categories = categoryRepository.findAllAvailableCategories();
        return categories.stream()
                         .map(CategoryToCategoryDtoConverter::convert)
                         .toList();
    }

    public CategoryDto save(Category category) {
        Boolean exists = this.existsByName(category.getName());
        if (exists) {
            return null;
        }
        Category savedCategory = categoryRepository.save(category);
        return CategoryToCategoryDtoConverter.convert(savedCategory);
    }

    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                                              .orElse(null);
        if (category != null) {
            return CategoryToCategoryDtoConverter.convert(category);
        }
        return null;
    }

    public Boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public CategoryDto findByName(String name) {
        Category category = categoryRepository.findByName(name);
        if (category != null) {
            return CategoryToCategoryDtoConverter.convert(category);
        }
        return null;
    }

    public CategoryDto update(Category category) {
        Boolean exists = this.existsByName(category.getName());
        if (exists) {
            return null;
        }
        Category updatedCategory = categoryRepository.save(category);
        return CategoryToCategoryDtoConverter.convert(updatedCategory);
    }

    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id)
                                              .orElse(null);
        if (category != null) {
            category.setIsDeleted(true);
            categoryRepository.save(category);
        }
    }
}
