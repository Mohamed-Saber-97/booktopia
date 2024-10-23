package org.example.booktopia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.model.Product;
import org.example.booktopia.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAllProductsByCategoryIds(Iterator<Long> categoryIds) {
        return productRepository.findAllByCategoryIds(categoryIds);
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("id", id.toString()));
    }
}
