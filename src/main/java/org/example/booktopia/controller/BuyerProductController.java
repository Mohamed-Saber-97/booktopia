package org.example.booktopia.controller;

import lombok.RequiredArgsConstructor;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.service.BuyerProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/buyer-products")
@RequiredArgsConstructor
public class BuyerProductController {

    private final BuyerProductService buyerProductService;

    @GetMapping("{buyerId}")
    public ResponseEntity<List<ProductDto>> getBuyerInterestedProducts(@PathVariable("buyerId") Long buyerId) {
        List<ProductDto> products = buyerProductService.getBuyerInterestedProducts(buyerId);
        return ResponseEntity.ok(products);
    }
}
