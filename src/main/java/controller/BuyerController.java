package controller;

import model.Buyer;
import model.Product;
import service.BuyerService;

public class BuyerController {
    private final BuyerService buyerService;

    public BuyerController() {
        this.buyerService = new BuyerService();
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
}
