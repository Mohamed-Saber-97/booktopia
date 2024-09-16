package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Account;
import model.Address;
import model.Buyer;
import model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.BuyerRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BuyerServiceTest {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private BuyerRepository buyerRepository;
    private BuyerService buyerService;
    private Buyer testBuyer;

    @BeforeEach
    public void setUp() {
        // Setup in-memory H2 database for testing
        entityManagerFactory = Persistence.createEntityManagerFactory("booktopia");
        entityManager = entityManagerFactory.createEntityManager();
        buyerRepository = new BuyerRepository();
        buyerService = new BuyerService();

        Account testAccount = Account.builder().name("Test User").birthday(LocalDate.of(1990, 1, 1)).password("securePassword").job("Software Engineer").email("testuser@example.com").phoneNumber("01592018345").address(new Address("123 Street", "City", "Country", "12345")) // Assuming Address is another embedded object
                .build();

        // Creating a test Buyer object
        testBuyer = new Buyer();

        // Setting the account
        testBuyer.setAccount(testAccount);

        // Setting credit limit
        testBuyer.setCreditLimit(BigDecimal.valueOf(1000.00));
    }

    @AfterEach
    public void tearDown() {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }

    @Test
    public void testSaveBuyer() {

        Buyer savedBuyer = buyerService.save(testBuyer);

        assertNotNull(savedBuyer.getId());
        assertEquals("testuser@example.com", savedBuyer.getAccount().getEmail());

        Buyer removalBuyer = buyerService.findByEmail("testuser@example.com");
        removalBuyer = entityManager.merge(removalBuyer);

        entityManager.getTransaction().begin();
        entityManager.remove(removalBuyer);
        entityManager.getTransaction().commit();
    }


    @Test
    public void testUpdateBuyer() {

        testBuyer = buyerService.save(testBuyer);
        testBuyer.setCreditLimit(new BigDecimal(9999));


        Buyer updatedBuyer = buyerService.update(testBuyer);


        assertNotNull(updatedBuyer);
        assertEquals(new BigDecimal(9999), updatedBuyer.getCreditLimit());

        Buyer removalBuyer = buyerService.findByEmail("testuser@example.com");
        removalBuyer = entityManager.merge(removalBuyer);

        entityManager.getTransaction().begin();
        entityManager.remove(removalBuyer);
        entityManager.getTransaction().commit();
    }

    @Test
    public void testFindInterestsByBuyerId() {

        testBuyer = buyerService.save(testBuyer);

        List<Product> interests = buyerService.findInterestsByBuyerId(testBuyer.getId());
        assertEquals(16, interests.size());


        Buyer removalBuyer = buyerService.findByEmail("testuser@example.com");
        removalBuyer = entityManager.merge(removalBuyer);

        entityManager.getTransaction().begin();
        entityManager.remove(removalBuyer);
        entityManager.getTransaction().commit();
    }

    @Test
    public void testCheckValidLoginCredentials() {

        testBuyer = buyerService.save(testBuyer);
        // Test correct credentials
        assertTrue(buyerService.checkValidLoginCredentials("testuser@example.com", "securePassword"));

        // Test incorrect credentials
        assertFalse(buyerService.checkValidLoginCredentials("logintest@example.com", "wrongpassword"));

        Buyer removalBuyer = buyerService.findByEmail("testuser@example.com");
        removalBuyer = entityManager.merge(removalBuyer);

        entityManager.getTransaction().begin();
        entityManager.remove(removalBuyer);
        entityManager.getTransaction().commit();
    }


}
