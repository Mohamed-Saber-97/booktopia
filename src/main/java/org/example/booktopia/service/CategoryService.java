package org.example.booktopia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.converters.CategoryToCategoryDtoConverter;
import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.error.DuplicateRecordFoundException;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.model.Category;
import org.example.booktopia.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;
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
        return categories.stream().map(CategoryToCategoryDtoConverter::convert).toList();
    }

    public Category save(Category category) {
        Boolean exists = this.existsByName(category.getName());
        if (exists) {
            Category existingCategory = findByName(category.getName());
            if (!existingCategory.getId().equals(category.getId())) {
                throw new DuplicateRecordFoundException("name", category.getName());
            }
        }
        return categoryRepository.save(category);
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("id", id.toString()));
    }

    public Boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public Category findByName(String name) {
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            throw new RecordNotFoundException("name", name);
        }
        return category;
    }

//    public Category update(Category category) {
//        Boolean exists = this.existsByName(category.getName());
//        if (exists) {
//            Category existingCategory = findByName(category.getName());
//            if (!existingCategory.getId().equals(category.getId())) {
//                throw new DuplicateRecordFoundException("name", category.getName());
//            }
//        }
//        return categoryRepository.save(category);
//    }

    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("id", id.toString()));
        category.setIsDeleted(true);
        categoryRepository.save(category);
    }
}
