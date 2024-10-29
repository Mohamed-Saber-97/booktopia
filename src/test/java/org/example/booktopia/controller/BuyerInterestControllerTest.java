package org.example.booktopia.controller;

import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.service.BuyerInterestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class BuyerInterestControllerTest {

    @InjectMocks
    private BuyerInterestController buyerInterestController;

    @Mock
    private BuyerInterestService buyerInterestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getBuyerInterestedCategories_withValidId_shouldReturnCategoryIds() {
        Long buyerId = 1L;
        List<Long> categoryIds = Arrays.asList(101L, 102L, 103L);

        when(buyerInterestService.findCategoryIdsByBuyerId(buyerId)).thenReturn(categoryIds);

        ResponseEntity<List<Long>> response = buyerInterestController.getBuyerInterestedCategories(buyerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(categoryIds, response.getBody());
    }

    @Test
    void getBuyerInterestedCategories_withNonExistentId_shouldThrowRecordNotFoundException() {
        Long buyerId = 999L;

        when(buyerInterestService.findCategoryIdsByBuyerId(anyLong())).thenThrow(new RecordNotFoundException("Buyer", "ID", buyerId.toString()));

        assertThrows(RecordNotFoundException.class, () -> {
            buyerInterestController.getBuyerInterestedCategories(buyerId);
        });
    }

    @Test
    void getBuyerInterestedCategories_withNoCategories_shouldReturnEmptyList() {
        Long buyerId = 2L;
        List<Long> emptyCategoryIds = Collections.emptyList();

        when(buyerInterestService.findCategoryIdsByBuyerId(buyerId)).thenReturn(emptyCategoryIds);

        ResponseEntity<List<Long>> response = buyerInterestController.getBuyerInterestedCategories(buyerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }
}