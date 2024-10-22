package org.example.booktopia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.dtos.CategoryDTO;
import org.example.booktopia.mapper.CategoryMapper;
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

    public List<CategoryDTO> findAllAvailableCategories() {
        List<Category> categories = categoryRepository.findAllAvailableCategories();
        return CategoryMapper.INSTANCE.toDTOs(categories);
    }

    public CategoryDTO save(Category category) {
        Boolean exists = this.existsByName(category.getName());
        if (exists) {
            return null;
        }
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.toDTO(savedCategory);
    }

    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                                              .orElse(null);
        if (category != null) {
            return CategoryMapper.INSTANCE.toDTO(category);
        }
        return null;
    }

    public Boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    public CategoryDTO findByName(String name) {
        Category category = categoryRepository.findByName(name);
        if (category != null) {
            return CategoryMapper.INSTANCE.toDTO(category);
        }
        return null;
    }

    public CategoryDTO update(Category category) {
        Boolean exists = this.existsByName(category.getName());
        if (exists) {
            return null;
        }
        Category updatedCategory = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.toDTO(updatedCategory);
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
