package org.example.booktopia.controller;

import lombok.RequiredArgsConstructor;
import org.example.booktopia.service.BuyerWishlistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/buyer-wishlist")
@RequiredArgsConstructor
public class BuyerWishlistController {
    private final BuyerWishlistService buyerWishlistService;

    @PostMapping("/add/{buyerId}/{productId}")
    public ResponseEntity<?> addProductToWishlist(@PathVariable("buyerId") Long buyerId, @PathVariable("productId") Long productId) {
        String result = buyerWishlistService.addProductToWishlist(buyerId, productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/remove/{buyerId}/{productId}")
    public ResponseEntity<?> removeProductFromWishlist(@PathVariable("buyerId") Long buyerId, @PathVariable("productId") Long productId) {
        String result = buyerWishlistService.removeProductFromWishlist(buyerId, productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(result);
    }

    @GetMapping("/{buyerId}")
    public ResponseEntity<?> getBuyerWishlist(@PathVariable("buyerId") Long buyerId) {
        return ResponseEntity.ok(buyerWishlistService.getBuyerWishlist(buyerId));
    }
}
