package service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Buyer;
import model.Product;
import repository.BuyerRepository;
import utils.EMFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BuyerService {
    private final BuyerRepository buyerRepository;
    private final ProductService productService;

    public BuyerService() {
        buyerRepository = new BuyerRepository();
        productService = new ProductService();
    }

    public Buyer save(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    public Buyer update(Buyer newBuyer) {
        Buyer existingBuyer = buyerRepository.findById(newBuyer.getId()).orElse(null);
        if (existingBuyer == null) {
            return null;
        }
        if (existingBuyer.hashCode() == newBuyer.hashCode()) {
            return existingBuyer;
        }
        existingBuyer.getAccount().setAddress(newBuyer.getAccount().getAddress());
        existingBuyer.setAccount(newBuyer.getAccount());
        existingBuyer.setCreditLimit(newBuyer.getCreditLimit());
        return buyerRepository.update(existingBuyer);
    }

    public boolean existsByEmail(String email) {
        return buyerRepository.existsByEmail(email);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return buyerRepository.existsByPhoneNumber(phoneNumber);
    }

    public List<Product> findInterestsByBuyerId(Long buyerId) {
        List<Product> interests = buyerRepository.findInterestsByBuyerId(buyerId);
        if (interests.isEmpty()) {
            return productService.findFirstX(16);
        }
        return interests.subList(0, Math.min(interests.size(), 16));
    }

    public Buyer findByEmail(String email) {
        Buyer buyer;
        try {
            buyer = buyerRepository.findByEmail(email);
        } catch (Exception e) {
            return null;
        }
        return buyer;
    }

    public boolean checkValidLoginCredentials(String email, String password) {
        Buyer buyer = findByEmail(email);
        if (buyer == null) {
            return false;
        }
        return buyer.getAccount().getPassword().equals(password);
    }

    public Buyer findById(Long id) {
        return buyerRepository.findById(id).orElse(null);
    }

    public void addProductToBuyerCart(Buyer buyer, Product product, int quantity) {
        buyerRepository.addProductToCart(buyer, product, quantity);
    }

    public void removeProductFromBuyerCart(Buyer buyer, Product product) {
        buyerRepository.removeProductFromCart(buyer, product);
    }

    public int incrementBuyerCartProductQuantity(Buyer buyer, Product product) {
        return buyerRepository.incrementProductQuantity(buyer, product);
    }

    public int decrementBuyerCartProductQuantity(Buyer buyer, Product product) {
        return buyerRepository.decrementProductQuantity(buyer, product);
    }

    public void clearBuyerCart(Buyer buyer) {
        buyerRepository.clearCart(buyer);
    }

    public Map<Product, Integer> retreiveBuyerCart(Buyer buyer) {
        return buyer.getCart();
    }

    public void setBuyerCartProductQuantity(Buyer buyer, Product product, int quantity) {
        buyerRepository.setProductQuantity(buyer, product, quantity);
    }

    public void addProductToBuyerWishlist(Buyer buyer, Product product) {
        buyerRepository.addToWishlist(buyer, product);
    }

    public void removeProductFromBuyerWishlist(Buyer buyer, Product product) {
        buyerRepository.removeFromWishlist(buyer, product);
    }

    public boolean validateAndUpdateCart(Buyer buyer) {
        if (buyer == null || buyer.getCart() == null) return false;
        int initialCartSize = buyer.getCart().size();
        EntityManager entityManager = EMFactory.getEMF("booktopia").createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Map<Product, Integer> currentCart = buyer.getCart();
            Set<Long> productIds = currentCart.keySet().stream().map(Product::getId).collect(Collectors.toSet());
            Map<Product, Integer> currentProducts = productService.findByIdsWithQuantities(productIds);
            buyer.clearCart();
            buyer.addCartItem(currentProducts);
            buyerRepository.update(buyer);
            entityManager.getTransaction().commit();
            return buyer.getCart().size() == initialCartSize;
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
                return false;
            }
        } finally {
            entityManager.close();
        }
        return false;
    }

}
