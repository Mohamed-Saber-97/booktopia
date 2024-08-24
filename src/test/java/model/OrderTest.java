package model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class OrderTest {
    private static EntityManager entityManager;
    private static EntityManagerFactory entityManagerFactory;

    @BeforeAll
    static void setUpBeforeClass() {
        entityManagerFactory = Persistence.createEntityManagerFactory("booktopia");
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    static void tearDownAfterClass() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void testOrderConstructorNoException() {
        assertDoesNotThrow(Order::new);
    }

    @Test
    void testOrderConstructorDAO() {
        entityManager.getTransaction().begin();
        Order order = new Order();
        entityManager.persist(order);
        entityManager.getTransaction().commit();
        assertDoesNotThrow(Order::new);
    }

    @Test
    void testOrderConstructorDAOModify() {
        entityManager.getTransaction().begin();
        Order order = new Order();
        entityManager.persist(order);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        order = entityManager.find(Order.class, 1);
        order.setStatus(Order.OrderStatus.SHIPPED);
        entityManager.getTransaction().commit();
        assertDoesNotThrow(Order::new);
    }
}