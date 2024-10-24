package org.example.booktopia.controller;

import lombok.RequiredArgsConstructor;
import org.example.booktopia.DTOs.CategoryDTO;
import org.example.booktopia.DTOs.ProductDTO;
import org.example.booktopia.mapper.CategoryMapper;
import org.example.booktopia.mapper.ProductMapper;
import org.example.booktopia.model.Category;
import org.example.booktopia.model.Product;
import org.example.booktopia.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductByID(@PathVariable long id) {

        return ResponseEntity.status(HttpStatus.OK)
                             .body(productService.findProductById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String name) {
        ProductDTO productDTO = productService.findByName(name);
        return ResponseEntity.ok(productDTO);
    }

    @PostMapping
    public ResponseEntity<ProductDTO> saveCategory(@RequestBody ProductDTO productDTO) {
       Product product = ProductMapper.INSTANCE.toEntity(productDTO);
        ProductDTO savedproductDto = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedproductDto);
    }
}
