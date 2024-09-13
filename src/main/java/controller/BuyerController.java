package controller;

import model.Buyer;
import model.Order;
import model.Product;
import service.BuyerService;

import java.util.List;

public class BuyerController {
    private final BuyerService buyerService;

    public BuyerController() {
        this.buyerService = new BuyerService();
    }

    public List<Buyer> findAll() {
        return buyerService.findAll();
    }

    public Buyer findById(Long id) {
        return buyerService.findById(id);
    }

    public Buyer save(Buyer buyer) {
        return buyerService.save(buyer);
    }

    public Buyer update(Buyer buyer) {
        return buyerService.update(buyer);
    }

    public void removeProductFromCart(Buyer buyer, Product product) {
        buyerService.removeProductFromBuyerCart(buyer, product);
    }

    public void addProductToBuyerCart(Buyer buyer, Product product, int quantity) {
        buyerService.addProductToBuyerCart(buyer, product, quantity);
    }

    public void setBuyerCartProductQuantity(Buyer buyer, Product product, int quantity) {
        buyerService.setBuyerCartProductQuantity(buyer, product, quantity);
    }

    public int incrementProductQuantity(Buyer buyer, Product product) {
        return buyerService.incrementBuyerCartProductQuantity(buyer, product);
    }

    public int decrementProductQuantity(Buyer buyer, Product product) {
        return buyerService.decrementBuyerCartProductQuantity(buyer, product);
    }

    public void addProductToBuyerWishlist(Buyer buyer, Product product) {
        buyerService.addProductToBuyerWishlist(buyer, product);
    }

    public void removeProductFromBuyerWishlist(Buyer buyer, Product product) {
        buyerService.removeProductFromBuyerWishlist(buyer, product);
    }

    public List<Buyer> search(int pageNumber, int pageSize) {
        return buyerService.search(pageNumber, pageSize);
    }

    public List<Order> searchOrders(Long buyerId, int pageNumber, int pageSize) {
        return buyerService.searchOrders(buyerId, pageNumber, pageSize);
    }
}
