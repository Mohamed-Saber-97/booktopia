package org.example.booktopia.controller;

import lombok.RequiredArgsConstructor;
import org.example.booktopia.service.BuyerInterestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/buyer-interests")
@RequiredArgsConstructor
public class BuyerInterestController {
    private final BuyerInterestService buyerInterestService;

    @GetMapping("{buyerId}")
    public ResponseEntity<List<Long>> getBuyerInterestedCategories(@PathVariable("buyerId") Long buyerId) {
        List<Long> categoryIds = buyerInterestService.findCategoryIdsByBuyerId(buyerId);
        return ResponseEntity.ok(categoryIds);
    }
}
