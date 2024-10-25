package org.example.booktopia.controller;

import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCategories_shouldReturnCategories() {
        CategoryDto category1 = new CategoryDto(1L, "Fiction");
        CategoryDto category2 = new CategoryDto(2L, "Non-Fiction");
        List<CategoryDto> categories = Arrays.asList(category1, category2);

        // Mock the service method
        when(categoryService.findAllAvailableCategories()).thenReturn(categories);

        ResponseEntity<List<CategoryDto>> response = categoryController.getAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categories, response.getBody());
        verify(categoryService).findAllAvailableCategories(); // Verify service method was called
    }

    @Test
    void getAvailableCategoriesById_shouldReturnAvailableCategories() {
        List<Long> ids = Arrays.asList(1L, 2L);
        List<Long> availableIds = Arrays.asList(1L);

        // Mock the service method
        when(categoryService.findAllAvailableCategoriesById(ids)).thenReturn(availableIds);

        ResponseEntity<List<Long>> response = categoryController.getAvailableCategoriesById(ids);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(availableIds, response.getBody());
        verify(categoryService).findAllAvailableCategoriesById(ids); // Verify service method was called
    }

    @Test
    void getCategoryById_shouldReturnCategory() {
        Long id = 1L;
        CategoryDto category = new CategoryDto(id, "Fiction");

        // Mock the service method
        when(categoryService.findById(id)).thenReturn(category);

        ResponseEntity<CategoryDto> response = categoryController.getCategoryById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
        verify(categoryService).findById(id); // Verify service method was called
    }

    @Test
    void saveCategory_shouldReturnCreatedCategory() {
        CategoryDto categoryDto = new CategoryDto(1L, "Fiction");

        // Mock the service method
        when(categoryService.save(categoryDto)).thenReturn(categoryDto);

        ResponseEntity<CategoryDto> response = categoryController.saveCategory(categoryDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(categoryDto, response.getBody());
        verify(categoryService).save(categoryDto); // Verify service method was called
    }

    @Test
    void getCategoryByName_shouldReturnCategory() {
        String name = "Fiction";
        CategoryDto categoryDto = new CategoryDto(1L, name);

        // Mock the service method
        when(categoryService.findByName(name)).thenReturn(categoryDto);

        ResponseEntity<CategoryDto> response = categoryController.getCategoryByName(name);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryDto, response.getBody());
        verify(categoryService).findByName(name); // Verify service method was called
    }

    @Test
    void updateCategory_shouldReturnUpdatedCategory() {
        Long id = 1L;
        CategoryDto categoryDto = new CategoryDto(id, "Fiction Updated");

        // Mock the service method
        when(categoryService.update(id, categoryDto)).thenReturn(categoryDto);

        ResponseEntity<CategoryDto> response = categoryController.updateCategory(id, categoryDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryDto, response.getBody());
        verify(categoryService).update(id, categoryDto); // Verify service method was called
    }

    @Test
    void deleteCategory_shouldReturnNoContent() {
        Long id = 1L;

        // Mock the service method
        doNothing().when(categoryService).deleteById(id);

        ResponseEntity<CategoryDto> response = categoryController.deleteCategory(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoryService).deleteById(id); // Verify service method was called
    }
}
