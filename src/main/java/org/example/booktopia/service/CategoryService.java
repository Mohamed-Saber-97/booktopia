package org.example.booktopia.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.dtos.CategoryDTO;
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
            CategoryDTO existingCategory = this.findByName(category.getName());
            if (!existingCategory.id().equals(category.getId())) {
                throw new DuplicateRecordException("name", category.getName());
            }
        }
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.toDTO(savedCategory);
    }


    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category", "ID", id.toString()));
        return CategoryMapper.INSTANCE.toDTO(category);
    }

    public CategoryDTO findByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new RecordNotFoundException("Category", "Name", name));
        return CategoryMapper.INSTANCE.toDTO(category);
    }

    public Boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDto) {
        Category currentCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Category", "ID", id.toString()));
        Boolean nameConflict = this.existsByName(categoryDto.name());

        if (nameConflict) {
            CategoryDTO existingCategory = this.findByName(categoryDto.name());
            if (!existingCategory.id().equals(id)) {
                throw new DuplicateRecordException("name", categoryDto.name());
            }
        }

        Category categoryToUpdate = CategoryMapper.INSTANCE.toEntity(categoryDto);
        categoryToUpdate.setId(id);

        Category updatedCategory = categoryRepository.save(categoryToUpdate);
        return CategoryMapper.INSTANCE.toDTO(updatedCategory);
    }


    @Transactional
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Category", "id", id.toString()));
        category.setIsDeleted(true);
        categoryRepository.save(category);
    }
}
