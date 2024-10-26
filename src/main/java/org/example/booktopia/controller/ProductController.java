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
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

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

    @GetMapping("/paging")
    public ResponseEntity<List<ProductDto>> searchProducts(
            @RequestParam Optional<String> name,
            @RequestParam Optional<String> category,
            @RequestParam Optional<String> minPrice,
            @RequestParam Optional<String> maxPrice,
            @RequestParam(defaultValue = "0") Integer pageNumber
    ) {
        List<Optional<String>> params = List.of(name, category, minPrice, maxPrice);
        System.out.println(params.size());
        List<ProductDto> products = productService.search(params, pageNumber, 16);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/first/{x}")
    public ResponseEntity<List<ProductDto>> getFirstProducts(@PathVariable Integer x) {
        List<ProductDto> products = productService.findFirst(x);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<ProductDto>> getProductsByCategoryIds(@RequestBody List<Long> categoryIds) {
        List<ProductDto> products = productService.findAllProductsByCategoryIds(categoryIds);
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

    @PostMapping("/isbn/{isbn}")
    public Boolean existsByIsbn(@PathVariable String isbn) {
        return productService.existsByIsbn(isbn);
    }

    @PostMapping
    public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto productDto) {
        Product product = productMapper.toEntity(productDto);
        System.out.println(product.getCategory());
        ProductDto savedProductDto = productService.save(product);
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
