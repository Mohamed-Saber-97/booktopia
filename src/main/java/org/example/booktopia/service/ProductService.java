package org.example.booktopia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.model.Product;
import org.example.booktopia.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final BuyerInterestService buyerInterestService;

    public List<Product> findAllProductsByCategoryIds(List<Long> categoryIds) {
        return productRepository.findAllByCategoryIds(categoryIds);
    }

}
