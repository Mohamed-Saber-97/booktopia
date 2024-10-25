package org.example.booktopia.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.error.DuplicateRecordException;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.mapper.ProductMapper;
import org.example.booktopia.model.Category;
import org.example.booktopia.model.Product;
import org.example.booktopia.repository.CategoryRepository;
import org.example.booktopia.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public List<ProductDto> findAllProductsByCategoryIds(List<Long> categoryIds) {
        List<Product> products = productRepository.findAllByCategoryIds(categoryIds);
        return productMapper.toDto(products);
    }

    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        return productMapper.toDto(products);
    }

    public List<ProductDto> findFirst(Integer x){
        List<Product> products = productRepository.findFirst(x);
        return productMapper.toDto(products);
    }

    public List<ProductDto> findAllAvailable() {
        List<Product> products = productRepository.findAllAvailableProducts();
        return productMapper.toDto(products);
    }

    public ProductDto findProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Product", "ID", id.toString()));
        return productMapper.toDto(product);
    }

    public ProductDto findByName(String name) {
        Product product = productRepository.findByName(name).orElseThrow(() -> new RecordNotFoundException("Product", "Name", name));
        return productMapper.toDto(product);
    }

    @Transactional
    public ProductDto save(ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        Category category = categoryRepository.findById(productDto.category().id())
                .orElseThrow(() -> new RecordNotFoundException("Category", "ID", productDto.category().id().toString()));
        product.setCategory(category);
        Boolean exists = this.existsByIsbn(product.getIsbn());
        if (exists) {
            ProductDto existingProduct = this.findByIsbn(product.getIsbn());
            if (!existingProduct.id().equals(product.getId())) {
                throw new DuplicateRecordException("ISBN", product.getIsbn());
            }
        }
        Product savedProduct = productRepository.save(product);
        return productMapper.toDto(savedProduct);
    }

    public ProductDto findByIsbn(String isbn) {
        Product product = productRepository.findByIsbn(isbn)
                .orElseThrow(() -> new RecordNotFoundException("Product", "ISBN", isbn));
        return productMapper.toDto(product);
    }

    public Boolean existsByIsbn(String isbn) {
        return productRepository.existsByIsbn(isbn);
    }

    @Transactional
    public ProductDto update(Long id, ProductDto productDto) {
        Product currentProduct = productRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Product", "ID", id.toString()));
        Boolean isbnConflict = this.existsByIsbn(productDto.isbn());
        if (isbnConflict) {
            ProductDto existingProduct = this.findByIsbn(productDto.isbn());
            if (!existingProduct.id().equals(id)) {
                throw new DuplicateRecordException("ISBN", productDto.isbn());
            }
        }
        Product updatedProduct = productMapper.toEntity(productDto);
        Category category = categoryRepository.findById(productDto.category().id())
                .orElseThrow(() -> new RecordNotFoundException("Category", "ID", productDto.category().id().toString()));
        updatedProduct.setId(id);
        updatedProduct.setCategory(category);
        Product savedProduct = productRepository.save(updatedProduct);
        return productMapper.toDto(savedProduct);
    }

    @Transactional
    public void deleteById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("product", "id", id.toString()));
        product.setIsDeleted(true);
        productRepository.save(product);
    }
}
