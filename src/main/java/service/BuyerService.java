package service;

import model.Buyer;
import model.Product;
import repository.BuyerRepository;

import java.util.List;
import java.util.Map;

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

    public Buyer findById(Long id) {
        return buyerRepository.findById(id).orElse(null);
    }

    public void addProductToBuyerCart(Buyer buyer, Product product, int quantity) {
        buyerRepository.addProductToCart(buyer, product, quantity);
    }

    public void removeProductFromBuyerCart(Buyer buyer, Product product) {
        buyerRepository.removeProductFromCart(buyer, product);
    }

    public void incrementBuyerCartProductQuantity(Buyer buyer, Product product) {
        buyerRepository.incrementProductQuantity(buyer, product);
    }

    public void decrementBuyerCartProductQuantity(Buyer buyer, Product product) {
        buyerRepository.decrementProductQuantity(buyer, product);
    }

    public void clearBuyerCart(Buyer buyer){
        buyerRepository.clearCart(buyer);
    }

    public Map<Product, Integer> retreiveBuyerCart(Buyer buyer){
        return buyer.getCart();
    }

    public void setBuyerCartProductQuantity(Buyer buyer, Product product, int quantity){
        buyerRepository.setProductQuantity(buyer, product, quantity);
    }

}
