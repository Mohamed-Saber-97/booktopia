package service;

import model.Product;
import repository.ProductRepository;

import java.util.List;

public class ProductService {
    ProductRepository productRepository;

    public ProductService() {
        productRepository = new ProductRepository();
    }

    public List<Product> findFirstX(int x) {
        List<Product> products = productRepository.findAll();
        return products.subList(0, Math.min(products.size(), x));
    }
}
