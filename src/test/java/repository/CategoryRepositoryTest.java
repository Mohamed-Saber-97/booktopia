package repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryRepositoryTest {

    @Test
    void shouldReturnFalseWhenCategoryNameDoesNotExist() {
        CategoryRepository categoryRepository = new CategoryRepository();
        String nonExistingCategoryName = "NonExistingCategory";
    
        boolean result = categoryRepository.existsByName(nonExistingCategoryName);
    
        assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenCategoryNameExists() {
        CategoryRepository categoryRepository = new CategoryRepository();
        String existingCategoryName = "enim"; // Replace with an actual existing category name

        boolean result = categoryRepository.existsByName(existingCategoryName);

        assertTrue(result);
    }


}