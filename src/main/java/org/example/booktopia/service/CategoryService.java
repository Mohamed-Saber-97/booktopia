package org.example.booktopia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
