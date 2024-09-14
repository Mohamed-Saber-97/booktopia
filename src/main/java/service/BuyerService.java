package service;

import error.InsufficientFunds;
import error.InsufficientStock;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.Buyer;
import model.Order;
import model.OrderProduct;
import model.Product;
import repository.BuyerRepository;
import utils.EMFactory;

import java.math.BigDecimal;
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

    public List<Buyer> findAll() {
        return buyerRepository.findAll();
    }

    public Buyer save(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    public Buyer update(Buyer newBuyer) {
        Buyer existingBuyer = buyerRepository.findById(newBuyer.getId()).orElse(null);
        System.out.println("Existing Buyer: " + existingBuyer.getInterests());
        System.out.println("New Buyer: " + newBuyer.getInterests());
        if (existingBuyer == null) {
            return null;
        }
        if (existingBuyer.hashCode() == newBuyer.hashCode()) {
            return existingBuyer;
        }
        existingBuyer.getAccount().setAddress(newBuyer.getAccount().getAddress());
        existingBuyer.setAccount(newBuyer.getAccount());
        existingBuyer.setCreditLimit(newBuyer.getCreditLimit());
        existingBuyer.setInterests(newBuyer.getInterests());
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


    public List<Buyer> search(int pageNumber, int pageSize) {
        return buyerRepository.search(pageNumber, pageSize);
    }

    public List<Order> searchOrders(Long buyerId, int pageNumber, int pageSize) {
        return buyerRepository.searchOrders(buyerId, pageNumber, pageSize);
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
            currentCart.entrySet().stream().filter(entry -> currentProducts.containsKey(entry.getKey())).forEach(entry -> currentProducts.put(entry.getKey(), entry.getValue()));
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

    public boolean validateAndUpdateWishList(Buyer buyer) {
        if (buyer == null || buyer.getWishlist() == null) return false;
        int initialWishlistSize = buyer.getWishlist().size();
        EntityManager entityManager = EMFactory.getEMF("booktopia").createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Set<Product> currentWishlist = buyer.getWishlist();
            Set<Long> productIds = currentWishlist.stream().map(Product::getId).collect(Collectors.toSet());
            Set<Product> availableProductsInWishlist = new HashSet<>(productService.findByIds(productIds));
            Set<Product> itemsToRemove = currentWishlist.stream().filter(item -> !availableProductsInWishlist.contains(item)).collect(Collectors.toSet());
            buyer.removeFromWishlist(itemsToRemove);
            buyerRepository.update(buyer);
            transaction.commit();
            return currentWishlist.size() == initialWishlistSize;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
                return false;
            }
        } finally {
            entityManager.close();
        }
        return false;
    }

    public boolean checkout(Buyer buyer) throws InsufficientStock, InsufficientFunds {
        if (buyer == null || buyer.getCart() == null) return false;
        EntityManager entityManager = EMFactory.getEMF("booktopia").createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Map<Product, Integer> currentCart = buyer.getCart();
            Order order = new Order(buyer);
            order = entityManager.merge(order);
            for (Map.Entry<Product, Integer> entry : currentCart.entrySet()) {
                Product product = entry.getKey();
                //--- Check if stock sufficient
                Integer productStock = product.getQuantity();
                Integer productQuantity = entry.getValue();
                if (productStock < productQuantity) {
                    throw new InsufficientStock("Transaction declined insufficient stock");
                }
                product.setQuantity(productStock - productQuantity);
                entityManager.merge(product);
                //-- Check is funds sufficient
                BigDecimal productPrice = product.getPrice();
                BigDecimal orderPrice = productPrice.multiply(new BigDecimal(productQuantity));
                BigDecimal buyerCreditLimit = buyer.getCreditLimit();
                if (orderPrice.compareTo(buyerCreditLimit) > 0) {
                    throw new InsufficientFunds("Transaction declined insufficient funds");
                }
                buyer.setCreditLimit(buyerCreditLimit.subtract(orderPrice));
                buyer.removeFromCart(product);
                entityManager.merge(buyer);
                //-- Add it to Orders
                OrderProduct orderProduct = new OrderProduct(order, entry);
                order.addOrderProduct(orderProduct);
                entityManager.merge(order);
            }
            transaction.commit();
            return buyer.getCart().isEmpty();
        } catch (InsufficientStock | InsufficientFunds e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        } finally {
            entityManager.close();
        }
    }
}
