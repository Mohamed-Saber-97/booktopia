package org.example.booktopia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.model.Product;
import org.example.booktopia.repository.ProductRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final BuyerInterestService buyerInterestService;
//Get ALL PRODUCTS
    public List<Product> AllProducts() {
        return productRepository.findAll();
    }

    //GET PRODUCT BY ID

    public Optional<Product> getProductById(Long id)
    {
        return productRepository.findById(id);

    }


    public List<Product> findAllProductsByCategoryIds(Iterator<Long> categoryIds) {
        return productRepository.findAllByCategoryIds(categoryIds);
    }


}
