package service;

import jakarta.persistence.EntityManager;
import model.Product;
import repository.ProductRepository;
import utils.EMFactory;

import java.util.List;
import java.util.Map;

public class ProductService {
    ProductRepository productRepository;
    EntityManager entityManager;

    public ProductService() {
        productRepository = new ProductRepository();
        entityManager = EMFactory.getEMF("booktopia").createEntityManager();
    }

    public List<Product> findFirstX(int x) {
        List<Product> products = productRepository.findAll();
        return products.subList(0, Math.min(products.size(), x));
    }

    public List<Product> search(Map<String, String> queryParameters, int pageNumber, int pageSize) {
        if (!queryParameters.isEmpty()) {
            return productRepository.search(queryParameters, pageNumber, pageSize);
        }
        return productRepository.search(queryParameters, pageNumber, pageSize);
    }
}
