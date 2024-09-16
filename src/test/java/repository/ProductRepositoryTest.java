package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Product;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductRepositoryTest {

    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("booktopia");
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    ProductRepository productRepository = new ProductRepository();


    @Test
    void testFindAllAvailable() {
        List<Product> availableProducts = productRepository.findAllAvailable();
        assertNotNull(availableProducts);
        assertNotEquals(1, availableProducts.size());
    }

    @Test
    void testFindAvailableProductById() {
        Product product = productRepository.findAvailableProductById(1L);
        assertNotNull(product);
        assertEquals("dolor", product.getName());
    }

    @Test
    void testSearchProducts() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("min_price", "40");
        queryParams.put("max_price", "120");

        List<Product> products = productRepository.search(queryParams, 0, 10);
        assertNotNull(products);
        assertEquals(10, products.size());
    }

    @Test
    void testExistsByIsbn() {
        boolean exists = productRepository.existsByIsbn("9785767562077");
        assertTrue(exists);
    }

    @AfterAll
    void tearDown() {
        if (entityManager != null) {
            entityManager.close();
        }
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}
