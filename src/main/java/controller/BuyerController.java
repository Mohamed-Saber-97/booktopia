package controller;

import model.Buyer;
import service.BuyerService;

public class BuyerController {
    private BuyerService buyerService;

    public BuyerController() {
        this.buyerService = new BuyerService();
    }

    public Buyer save(Buyer buyer) {
        return buyerService.save(buyer);
    }
}
