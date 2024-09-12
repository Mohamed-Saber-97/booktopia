package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CategoryServiceIntegrationTest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        // Initialize the EntityManagerFactory and EntityManager
        entityManagerFactory = Persistence.createEntityManagerFactory("booktopia");
        entityManager = entityManagerFactory.createEntityManager();

        // Initialize the CategoryService
        categoryService = new CategoryService();

        // Ensure data is present in the database (insert test data if needed)
        // Here, you assume that the data is already present in the database.
    }

    @AfterEach
    void tearDown() {
        // Close the EntityManager and EntityManagerFactory
        if (entityManager.isOpen()) {
            entityManager.close();
        }
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    @Test
    void testFindAll() {
        // Begin a transaction
        entityManager.getTransaction().begin();

        // Test the service method
        List<Category> categories = categoryService.findAll();

        // Commit transaction
        entityManager.getTransaction().commit();

        // Verify the results
        assertNotNull(categories);
        assertTrue(categories.size() > 0); // Check if there are categories in the list
        assertNotNull(categories.stream().filter(c -> c.getName().equals("neque")).findFirst().orElse(null));
    }

    @Test
    void testFindById() {
        // Begin a transaction
        entityManager.getTransaction().begin();

        // Test the service method
        Category category = categoryService.findById(2L);

        // Commit transaction
        entityManager.getTransaction().commit();

        // Verify the results
        assertNotNull(category);
        assertEquals("neque", category.getName());
    }

    @Test
    void testSave() {
        // Begin a transaction
        entityManager.getTransaction().begin();

        // Create a new category
        Category newCategory = new Category();
        newCategory.setName("Test");

        // Save the new category
        Category savedCategory = categoryService.save(newCategory);

        // Commit transaction
        entityManager.getTransaction().commit();

        // Verify the results
        assertNotNull(savedCategory);
        assertNotNull(savedCategory.getId()); // ID should be generated
        assertEquals("Test", savedCategory.getName());

        // Clean up
        entityManager.getTransaction().begin();
        savedCategory = entityManager.merge(savedCategory);
        entityManager.remove(savedCategory);
        entityManager.getTransaction().commit();
    }

    @Test
    void testUpdate() {
        // Begin a transaction
        entityManager.getTransaction().begin();

        // Retrieve an existing category
        Category category = categoryService.findById(4L);
        assertNotNull(category);

        // Update the category
        category.setName("Updated Category");
        Category updatedCategory = categoryService.update(category);

        // Commit transaction
        entityManager.getTransaction().commit();

        // Verify the results
        assertNotNull(updatedCategory);
        assertEquals("Updated Category", updatedCategory.getName());
    }

    @Test
    void testExistsByName() {
        // Begin a transaction
        entityManager.getTransaction().begin();

        // Test the service method
        boolean exists = categoryService.existsByName("neque");

        // Commit transaction
        entityManager.getTransaction().commit();

        // Verify the results
        assertTrue(exists);
    }
}
