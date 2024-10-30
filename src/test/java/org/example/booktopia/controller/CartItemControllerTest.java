package org.example.booktopia.controller;

import org.example.booktopia.dtos.CartItemDto;
import org.example.booktopia.service.CartItemService;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CartItemControllerTest {

    @InjectMocks
    private CartItemController cartItemController;

    @Mock
    private CartItemService cartItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCartItem_withValidData_shouldReturnCreated() {
        Long buyerId = 1L;
        Long productId = 2L;
        Integer quantity = 3;
        String expectedMessage = "Product added to cart";

        // Mock the service method
        when(cartItemService.addCartItem(buyerId, productId, quantity)).thenReturn(expectedMessage);

        ResponseEntity<?> response = cartItemController.addCartItem(buyerId, productId, quantity);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
        verify(cartItemService).addCartItem(buyerId, productId, quantity); // Verify service method was called
    }

    @Test
    void getCartItemsByBuyerId_withValidId_shouldReturnCartItems() {
        Long buyerId = 1L;
        CartItemDto cartItem1 = new CartItemDto(
                buyerId,
                2L,
                2,
                "Product 1",
                "Description of Product 1",
                "Author 1",
                "ISBN001",
                LocalDate.of(2022, 1, 1),
                1L,
                "imagePath1.jpg",
                BigDecimal.valueOf(100.00)
        );
        CartItemDto cartItem2 = new CartItemDto(
                buyerId,
                3L,
                1,
                "Product 2",
                "Description of Product 2",
                "Author 2",
                "ISBN002",
                LocalDate.of(2023, 1, 1),
                2L,
                "imagePath2.jpg",
                BigDecimal.valueOf(150.00)
        );
        List<CartItemDto> cartItems = Arrays.asList(cartItem1, cartItem2);

        // Mock the service method
        when(cartItemService.getCartItems(buyerId)).thenReturn(cartItems);

        ResponseEntity<List<CartItemDto>> response = cartItemController.getCartItemsByBuyerId(buyerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cartItems, response.getBody());
        verify(cartItemService).getCartItems(buyerId); // Verify service method was called
    }

    @Test
    void removeCartItem_withValidIds_shouldReturnNoContent() {
        Long buyerId = 1L;
        Long productId = 2L;
        String expectedMessage = "Product removed from cart";

        // Mock the service method
        when(cartItemService.removeCartItem(buyerId, productId)).thenReturn(expectedMessage);

        ResponseEntity<?> response = cartItemController.removeCartItem(buyerId, productId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(expectedMessage, response.getBody());
        verify(cartItemService).removeCartItem(buyerId, productId); // Verify service method was called
    }

    @Test
    void incrementProductQuantity_withValidIds_shouldReturnOk() {
        Long buyerId = 1L;
        Long productId = 2L;
        Integer expectedQuantity = 4;

        // Mock the service method
        when(cartItemService.incrementProductQuantity(buyerId, productId)).thenReturn(expectedQuantity);

        ResponseEntity<?> response = cartItemController.incrementProductQuantity(buyerId, productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedQuantity, response.getBody());
        verify(cartItemService).incrementProductQuantity(buyerId, productId); // Verify service method was called
    }

    @Test
    void decrementProductQuantity_withValidIds_shouldReturnOk() {
        Long buyerId = 1L;
        Long productId = 2L;
        Integer expectedQuantity = 2;

        // Mock the service method
        when(cartItemService.decrementProductQuantity(buyerId, productId)).thenReturn(expectedQuantity);

        ResponseEntity<?> response = cartItemController.decrementProductQuantity(buyerId, productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedQuantity, response.getBody());
        verify(cartItemService).decrementProductQuantity(buyerId, productId); // Verify service method was called
    }

    @Test
    void clearCart_withValidId_shouldReturnOk() {
        Long buyerId = 1L;

        // Mock the service method
        doNothing().when(cartItemService).clearCart(buyerId);

        ResponseEntity<?> response = cartItemController.clearCart(buyerId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(cartItemService).clearCart(buyerId); // Verify service method was called
    }
}
