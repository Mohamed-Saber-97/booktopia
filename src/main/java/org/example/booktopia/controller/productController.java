package org.example.booktopia.controller;

import org.example.booktopia.model.Product;
import org.example.booktopia.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class productController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.AllProducts();
    }

    @GetMapping("{/id}")
    public ResponseEntity<Product> getProductByID(@PathVariable long id){
        return productService.getProductById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

}
