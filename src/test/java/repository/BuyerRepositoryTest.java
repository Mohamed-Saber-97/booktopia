package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import model.Buyer;
import model.Order;
import model.Product;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.BuyerRepository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class BuyerRepositoryTest {

    private BuyerRepository buyerRepository;
    private Buyer buyerWithOrders;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("booktopia");
    private EntityManager em = emf.createEntityManager();
    @BeforeEach
    void setUp() {

        buyerRepository = new BuyerRepository();
        // Set up buyerWithOrders with orders in the database
    }

    @Test
    void findOrdersByBuyerId_buyerWithOrdersExists_returnsOrders() {
        Buyer buyer = em.find(Buyer.class , 2);
        em.getTransaction().begin();
        em.getTransaction().commit();

        List<Order> orders = buyerRepository.findOrdersByBuyerId(buyer.getId());
        assertNotNull(orders);
        assertEquals(buyer.getOrders().size(), orders.size());
    }



    @Test
    void findOrdersByBuyerId_nonExistentBuyerId_returnsEmptyList() {
        Long nonExistentBuyerId = 9999L;
        List<Order> orders = buyerRepository.findOrdersByBuyerId(nonExistentBuyerId);
        assertNotNull(orders);
        assertEquals(0, orders.size());
    }
    
    @Test
    void existsByEmail_nonExistentEmail_returnsFalse() {
        String nonExistentEmail = "nonexistent@email.com";
        boolean exists = buyerRepository.existsByEmail(nonExistentEmail);
        assertFalse(exists);
    }
    
    @Test
    void existsByPhoneNumber_nonExistentPhoneNumber_returnsFalse() {
        String nonExistentPhoneNumber = "1234567890";
        boolean exists = buyerRepository.existsByPhoneNumber(nonExistentPhoneNumber);
        assertFalse(exists);
    }
    @Test
    void findInterestsByBuyerId_buyerWithNoInterests_returnsEmptyList() {
        // Given
        Optional <Buyer> b= buyerRepository.findById(2L);
        try {
            Buyer buyer = b.get();
            List<Product> interests = buyerRepository.findInterestsByBuyerId(buyer.getId());

            // Then
            assertNotNull(interests);
            assertFalse(buyer.getInterests().size() >= interests.size());
        }
        catch (Exception e) {
            assertEquals(1,2);
        }
        // When

    }
    @Test
    void findByEmail_nonExistentEmail_throwsException() {
        String nonExistentEmail = "nonexistent@email.com";
        assertThrows(NoResultException.class, () -> buyerRepository.findByEmail(nonExistentEmail));
    }

    @Test
    void addProductToCart_newProduct_addsProductToCart() {
        // Given

        Buyer buyer = em.find(Buyer.class , 1);
        Product product = em.find(Product.class , 5);
        em.getTransaction().begin();
        em.getTransaction().commit();

        int quantity = 3;

        int sizeShouldBe = buyer.getCart().containsKey(product) ? buyer.getCart().size() : buyer.getCart().size()+1;
        BuyerRepository buyerRepository = new BuyerRepository();

        // When
        buyerRepository.addProductToCart(buyer, product, quantity);

        // Then
        Map<Product, Integer> cart = buyer.getCart();
        assertNotNull(cart);
        assertEquals(sizeShouldBe, cart.size());
        assertEquals(quantity, cart.get(product));
    }

    @Test
    void removeProductFromCart_productPresentInCart_removesProductFromCart() {
        // Given


        Buyer buyer = em.find(Buyer.class , 1);
        Product product = em.find(Product.class , 5);
        em.getTransaction().begin();
        em.getTransaction().commit();

        buyer.addToCart(product, 3);
        int sizeBefore = buyer.getCart().size();

        BuyerRepository buyerRepository = new BuyerRepository();

        // When
        buyerRepository.removeProductFromCart(buyer, product);

        int sizeAfter = buyer.getCart().size();
        // Then
        Map<Product, Integer> cart = buyer.getCart();
        assertNotNull(cart);
        assertEquals(sizeBefore, sizeAfter +1);
    }

    @Test
    void incrementProductQuantity_productPresentInCart_increasesQuantityBy1() {
        // Given
        Buyer buyer = em.find(Buyer.class , 1);
        Product product = em.find(Product.class , 4);
        em.getTransaction().begin();
        em.getTransaction().commit();

        int cartSize = buyer.getCart().containsKey(product) ? buyer.getCart().size() : buyer.getCart().size()+1;

        buyer.addToCart(product, 500);


        BuyerRepository buyerRepository = new BuyerRepository();

        // When
        int quantity = buyer.getCart().get(product);
        int newQuantity = buyerRepository.incrementProductQuantity(buyer, product);

        // Then
        Map<Product, Integer> cart = buyer.getCart();
        assertNotNull(cart);
        assertEquals(cartSize, cart.size());
        assertEquals(quantity+1, newQuantity);
    }



    @Test
    void decrementProductQuantity_productQuantityIs1_removesProductFromCart() {
        // Given
        Buyer buyer = em.find(Buyer.class , 1);
        Product product = em.find(Product.class , 3);
        em.getTransaction().begin();
        em.getTransaction().commit();
        buyer.addToCart(product, 50);
        BuyerRepository buyerRepository = new BuyerRepository();

        // When
        int quantity = buyer.getCart().get(product);
        int newQuantity = buyerRepository.decrementProductQuantity(buyer, product);

        // Then
        Map<Product, Integer> cart = buyer.getCart();
        assertNotNull(cart);
        assertEquals(quantity-1, newQuantity);
    }
/*
    @AfterAll
    void tearDown() {
        if (em != null) {
            em.close();
        }
        if (emf != null) {
            emf.close();
        }
    }
*/
    }