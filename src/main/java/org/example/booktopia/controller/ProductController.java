package org.example.booktopia.controller;

import lombok.RequiredArgsConstructor;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.mapper.ProductMapper;
import org.example.booktopia.model.Product;
import org.example.booktopia.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/available-products")
    public ResponseEntity<List<ProductDto>> getAllAvailableProducts() {
        List<ProductDto> products = productService.findAllAvailable();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductByID(@PathVariable long id) {
        ProductDto productDto = productService.findProductById(id);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ProductDto> getProductByName(@PathVariable String name) {
        ProductDto productDto = productService.findByName(name);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<ProductDto> getProductByIsbn(@PathVariable String isbn) {
        ProductDto productDto = productService.findByIsbn(isbn);
        return ResponseEntity.ok(productDto);
    }

    @PostMapping
    public ResponseEntity<ProductDto> saveCategory(@RequestBody ProductDto productDto) {
        ProductDto savedProductDto = productService.save(productDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedProductDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        ProductDto updatedProductDto = productService.update(id, productDto);
        return ResponseEntity.ok(updatedProductDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
