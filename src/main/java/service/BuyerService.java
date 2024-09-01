package service;

import model.Buyer;
import repository.BuyerRepository;

public class BuyerService {
    private BuyerRepository buyerRepository;

    public BuyerService() {
        buyerRepository = new BuyerRepository();
    }

    public Buyer save(Buyer buyer) {
        return buyerRepository.save(buyer);
    }
}
