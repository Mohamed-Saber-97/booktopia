package org.example.booktopia.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.DTOs.ProductDTO;
import org.example.booktopia.DTOs.ProductDTO;
import org.example.booktopia.error.DuplicateRecordException;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.mapper.ProductMapper;
import org.example.booktopia.mapper.ProductMapper;
import org.example.booktopia.model.Product;
import org.example.booktopia.model.Product;
import org.example.booktopia.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> findAllProductsByCategoryIds(List<Long> ProductIds) {
        return productRepository.findAllByCategoryIds(ProductIds);
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

    @Transactional
    public void deleteById(Long id)
    {
        Product product = productRepository.findById(id).orElseThrow(()->new RecordNotFoundException("product","ID",id.toString()));
        product.setIsDeleted(true);
        productRepository.save(product);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO ProductDTO) {
        Product currentproduct = productRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Product", "ID",
                        id.toString()));
        Boolean nameConflict = productRepository.existsByNameAndIdNotAndIsDeletedIsFalse(ProductDTO.name(), id);
        if (nameConflict) {
            throw new DuplicateRecordException("Product", ProductDTO.name());
        }

        Product ProductToUpdate = ProductMapper.INSTANCE.toEntity(ProductDTO);
        ProductToUpdate.setId(id);

        Product updatedProduct = productRepository.save(ProductToUpdate);
        return ProductMapper.INSTANCE.toDTO(updatedProduct);
    }


}
