package controller;

import model.Product;
import service.ProductService;

import java.util.List;
import java.util.Map;

public class ProductController {
    private ProductService productService;

    public ProductController() {
        productService = new ProductService();
    }

    public Product findById(Long id) {
        return productService.findById(id);
    }

    public List<Product> search(Map<String, String> queryParameters, int pageNumber, int pageSize) {
        return productService.search(queryParameters, pageNumber, pageSize);
    }

    public Product findAvailableProductById(Long id) {
        return productService.findAvailableProductById(id);
    }

    public List<Product> findAllAvailable() {
        return productService.findAllAvailable();
    }

    public Product save(Product product) {
        return productService.save(product);
    }
}
