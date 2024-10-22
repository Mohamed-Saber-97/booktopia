package org.example.booktopia.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.DTOs.ProductDTO;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.model.Category;
import org.example.booktopia.model.Product;
import org.example.booktopia.repository.ProductRepository;
import org.springframework.data.jpa.repository.Query;
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
                .orElseThrow(() -> new RecordNotFoundException("id", id.toString()));
    }




    public Product addProduct(ProductDTO productDTO) {
        // Check if the ISBN already exists
        if (productRepository.existByIsbn(productDTO.getIsbn()) >= 1) {
            throw new IllegalArgumentException("ISBN already exists");
        }

        // Convert DTO to entity
        Product product = new Product();
        product.setAuthor(productDTO.getAuthor());
        product.setDescription(productDTO.getDescription());
        product.setIsbn(productDTO.getIsbn());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        product.setReleaseDate(productDTO.getReleaseDate());



        return productRepository.save(product);
    }

}



