package org.example.booktopia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.dtos.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuyerProductService {
    private final BuyerService buyerService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final BuyerInterestService buyerInterestService;

    public List<ProductDto> getBuyerInterestedProducts(Long buyerId) {
        List<Long> categoryIds = buyerInterestService.findCategoryIdsByBuyerId(buyerId);
        List<Long> availableCategoryIds = categoryService.findAllAvailableCategoriesById(categoryIds);
        return productService.findAllProductsByCategoryIds(availableCategoryIds);
    }
}
