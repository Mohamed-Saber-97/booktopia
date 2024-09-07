package service;

import model.Buyer;
import model.Product;
import repository.BuyerRepository;

import java.util.List;
import java.util.Map;

public class BuyerService {
    private BuyerRepository buyerRepository;
    private ProductService productService;

    public BuyerService() {
        buyerRepository = new BuyerRepository();
        productService = new ProductService();
    }

    public Buyer save(Buyer buyer) {
        return buyerRepository.save(buyer);
    }
    public Buyer update(Buyer buyer) {
        Buyer savedBuyer = findByEmail(buyer.getAccount().getEmail());
        savedBuyer.getAccount().setAddress(buyer.getAccount().getAddress());
        savedBuyer.setAccount(buyer.getAccount());
        savedBuyer.setCreditLimit(buyer.getCreditLimit());
        return buyerRepository.update(savedBuyer);
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
        return buyerRepository.findByEmail(email);
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

    public void addProductToBuyerWishlist(Buyer buyer, Product product){
        buyerRepository.addToWishlist(buyer, product);
    }

    public void removeProductFromBuyerWishlist(Buyer buyer, Product product){
        buyerRepository.removeFromWishlist(buyer, product);
    }

}
