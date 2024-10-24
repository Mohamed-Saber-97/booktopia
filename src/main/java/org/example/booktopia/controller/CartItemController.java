package org.example.booktopia.controller;

import lombok.RequiredArgsConstructor;
import org.example.booktopia.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
public class CartItemController {
    private final CartItemService cartItemService;

    @PostMapping("/add/{buyerId}/{productId}/{quantity}")
    public ResponseEntity<?> addCartItem(@PathVariable("buyerId") Long buyerId, @PathVariable("productId") Long productId, @PathVariable("quantity") Integer quantity) {
        cartItemService.addCartItem(buyerId, productId, quantity);
        return ResponseEntity.ok().build();
    }
}
