package org.example.booktopia.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.DTOs.CategoryDTO;
import org.example.booktopia.DTOs.ProductDTO;
import org.example.booktopia.error.DuplicateRecordException;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.mapper.CategoryMapper;
import org.example.booktopia.mapper.ProductMapper;
import org.example.booktopia.model.Category;
import org.example.booktopia.model.Product;
import org.example.booktopia.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAllProductsByCategoryIds(List<Long> categoryIds) {
        return productRepository.findAllByCategoryIds(categoryIds);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id)
                                .orElseThrow(() -> new RecordNotFoundException("Product", "ID", id.toString()));
    }

    public ProductDTO findByName(String name) {
        Product product = productRepository.findByName(name).orElseThrow (()-> new RecordNotFoundException("Product", "ID",
                name.toString()));
        return ProductMapper.INSTANCE.toDTO(product);
    }

    @Transactional
    public ProductDTO save(Product product) {
        Boolean exists = productRepository.existsByName(product.getName());
        if (exists) {
            throw new DuplicateRecordException("Product", product.getName());
        }
        Product savedProduct = productRepository.save(product);
        return ProductMapper.INSTANCE.toDTO(savedProduct);
    }

}
