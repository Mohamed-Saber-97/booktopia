package org.example.booktopia.controller;

import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.service.BuyerProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class BuyerProductControllerTest {

    @InjectMocks
    private BuyerProductController buyerProductController;

    @Mock
    private BuyerProductService buyerProductService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBuyerInterestedProducts_withValidId_shouldReturnProductDtos() {
        Long buyerId = 1L;
        List<ProductDto> products = Arrays.asList(
                new ProductDto(1L, "Product 1", "Description 1",
                        "Author 1", "ISBN001", LocalDate.now(),
                        new BigDecimal(1), 10, "image1.png", new CategoryDto(1L, "Category 1")),
                new ProductDto(2L, "Product 2", "Description 2",
                        "Author 2", "ISBN002", LocalDate.now(),
                        new BigDecimal(2), 150, "image2.png", new CategoryDto(2L, "Category 2"))
        );

        when(buyerProductService.getBuyerInterestedProducts(buyerId)).thenReturn(products);

        ResponseEntity<List<ProductDto>> response = buyerProductController.getBuyerInterestedProducts(buyerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(products, response.getBody());
    }


    @Test
    void getBuyerInterestedProducts_withNonExistentId_shouldThrowRecordNotFoundException() {
        Long buyerId = 999L;

        when(buyerProductService.getBuyerInterestedProducts(anyLong())).thenThrow(new RecordNotFoundException("Buyer", "ID", buyerId.toString()));

        assertThrows(RecordNotFoundException.class, () -> {
            buyerProductController.getBuyerInterestedProducts(buyerId);
        });
    }

    @Test
    void getBuyerInterestedProducts_withNoInterestedProducts_shouldReturnEmptyList() {
        Long buyerId = 2L;
        List<ProductDto> emptyProductList = Collections.emptyList();

        when(buyerProductService.getBuyerInterestedProducts(buyerId)).thenReturn(emptyProductList);

        ResponseEntity<List<ProductDto>> response = buyerProductController.getBuyerInterestedProducts(buyerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }
}