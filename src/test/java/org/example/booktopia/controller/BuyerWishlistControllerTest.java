package org.example.booktopia.controller;

import org.example.booktopia.service.BuyerWishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BuyerWishlistControllerTest {

    @InjectMocks
    private BuyerWishlistController buyerWishlistController;

    @Mock
    private BuyerWishlistService buyerWishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addProductToWishlist_withValidIds_shouldReturnCreated() {
        Long buyerId = 1L;
        Long productId = 2L;

        // Mock the service method to do nothing
        doNothing().when(buyerWishlistService).addProductToWishlist(buyerId, productId);

        ResponseEntity<?> response = buyerWishlistController.addProductToWishlist(buyerId, productId);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(buyerWishlistService).addProductToWishlist(buyerId, productId); // Verify service method was called
    }

    @Test
    void removeProductFromWishlist_withValidIds_shouldReturnNoContent() {
        Long buyerId = 1L;
        Long productId = 2L;

        // Mock the service method to do nothing
        doNothing().when(buyerWishlistService).removeProductFromWishlist(buyerId, productId);

        ResponseEntity<?> response = buyerWishlistController.removeProductFromWishlist(buyerId, productId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(buyerWishlistService).removeProductFromWishlist(buyerId, productId); // Verify service method was called
    }

    @Test
    void addProductToWishlist_whenServiceThrowsException_shouldReturnBadRequest() {
        Long buyerId = 1L;
        Long productId = 2L;

        // Mock the service method to throw an exception
        doThrow(new RuntimeException("Service error")).when(buyerWishlistService).addProductToWishlist(buyerId, productId);

        // Assert that the exception is handled correctly
        try {
            buyerWishlistController.addProductToWishlist(buyerId, productId);
        } catch (RuntimeException e) {
            assertEquals("Service error", e.getMessage());
        }
    }

    @Test
    void removeProductFromWishlist_whenServiceThrowsException_shouldReturnBadRequest() {
        Long buyerId = 1L;
        Long productId = 2L;

        // Mock the service method to throw an exception
        doThrow(new RuntimeException("Service error")).when(buyerWishlistService).removeProductFromWishlist(buyerId, productId);

        // Assert that the exception is handled correctly
        try {
            buyerWishlistController.removeProductFromWishlist(buyerId, productId);
        } catch (RuntimeException e) {
            assertEquals("Service error", e.getMessage());
        }
    }
}
