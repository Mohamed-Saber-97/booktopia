package org.example.booktopia.controller;

import lombok.RequiredArgsConstructor;
import org.example.booktopia.dtos.CartItemDto;
import org.example.booktopia.service.CartItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping("/add/{buyerId}/{productId}/{quantity}")
    public ResponseEntity<?> addCartItem(@PathVariable("buyerId") Long buyerId, @PathVariable("productId") Long productId, @PathVariable("quantity") Integer quantity) {
        String result = cartItemService.addCartItem(buyerId, productId, quantity);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<CartItemDto>> getCartItemsByBuyerId(@PathVariable("buyerId") Long buyerId) {
        return ResponseEntity.ok(cartItemService.getCartItems(buyerId));
    }

    @DeleteMapping("/remove/{buyerId}/{productId}")
    public ResponseEntity<?> removeCartItem(@PathVariable("buyerId") Long buyerId, @PathVariable("productId") Long productId) {
        cartItemService.removeCartItem(buyerId, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Product removed from cart");
    }

    @PostMapping("/increment/{buyerId}/{productId}")
    public ResponseEntity<?> incrementProductQuantity(@PathVariable("buyerId") Long buyerId, @PathVariable("productId") Long productId) {
        cartItemService.incrementProductQuantity(buyerId, productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/decrement/{buyerId}/{productId}")
    public ResponseEntity<?> decrementProductQuantity(@PathVariable("buyerId") Long buyerId, @PathVariable("productId") Long productId) {
        cartItemService.decrementProductQuantity(buyerId, productId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/clear/{buyerId}")
    public ResponseEntity<?> clearCart(@PathVariable("buyerId") Long buyerId) {
        cartItemService.clearCart(buyerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
