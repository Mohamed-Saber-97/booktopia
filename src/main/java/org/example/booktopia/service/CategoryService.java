package org.example.booktopia.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.error.DuplicateRecordException;
import org.example.booktopia.error.RecordNotFoundException;
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
    private final CategoryMapper categoryMapper;

    public List<Long> findAllAvailableCategoriesById(List<Long> ids) {
        return categoryRepository.findAllIdByAndIsDeletedIsFalse(ids);
    }

    public List<CategoryDto> findAllAvailableCategories() {
        List<Category> categories = categoryRepository.findAllAvailableCategories();
        return categoryMapper.toDto(categories);
    }

    public CategoryDto save(CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        Boolean exists = this.existsByName(category.getName());
        if (exists) {
            CategoryDto existingCategory = this.findByName(category.getName());
            if (!existingCategory.id().equals(category.getId())) {
                throw new DuplicateRecordException("name", category.getName());
            }
        }
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.toDto(savedCategory);
    }


    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category", "ID",
                        id.toString()));
        return categoryMapper.toDto(category);
    }

    public CategoryDto findByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException("Category", "Name", name));
        return categoryMapper.toDto(category);
    }

    public Boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Transactional
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        Category currentCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category", "ID", id.toString()));
        Boolean nameConflict = this.existsByName(categoryDto.name());

        if (nameConflict) {
            CategoryDto existingCategory = this.findByName(categoryDto.name());
            if (!existingCategory.id().equals(id)) {
                throw new DuplicateRecordException("name", categoryDto.name());
            }
        }

        Category categoryToUpdate = categoryMapper.toEntity(categoryDto);
        categoryToUpdate.setId(id);

        Category updatedCategory = categoryRepository.save(categoryToUpdate);
        return categoryMapper.toDto(updatedCategory);
    }

    @Transactional
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category", "id", id.toString()));
        category.setIsDeleted(true);
        categoryRepository.save(category);
    }
}
