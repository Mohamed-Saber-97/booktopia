package org.example.booktopia.controller;

import lombok.RequiredArgsConstructor;
//import org.example.booktopia.DTOs.ProductDTO;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping
//    public ResponseEntity<Product> addProduct(@RequestBody ProductDTO productDTO) {
//        Product product = productService.addProduct(productDTO);
//        return ResponseEntity.ok(product);
//    }



}
