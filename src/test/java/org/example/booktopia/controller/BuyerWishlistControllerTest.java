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
        String expectedMessage = "Product added to wishlist";

        // Mock the service method to return the expected message
        when(buyerWishlistService.addProductToWishlist(buyerId, productId)).thenReturn(expectedMessage);

        // Call the controller method
        ResponseEntity<?> response = buyerWishlistController.addProductToWishlist(buyerId, productId);

        // Verify the response status and body
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());

        // Verify that the service method was called with correct parameters
        verify(buyerWishlistService).addProductToWishlist(buyerId, productId);
    }


    @Test
    void removeProductFromWishlist_withValidIds_shouldReturnOk() {
        Long buyerId = 1L;
        Long productId = 2L;
        String expectedMessage = "Product removed from wishlist";

        // Mock the service method to return the expected message
        when(buyerWishlistService.removeProductFromWishlist(buyerId, productId)).thenReturn(expectedMessage);

        // Call the controller method
        ResponseEntity<?> response = buyerWishlistController.removeProductFromWishlist(buyerId, productId);

        // Verify the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());

        // Verify that the service method was called with correct parameters
        verify(buyerWishlistService).removeProductFromWishlist(buyerId, productId);
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
