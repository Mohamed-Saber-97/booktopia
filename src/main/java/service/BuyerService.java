package service;

import model.Buyer;
import model.Product;
import repository.BuyerRepository;

import java.util.List;

public class BuyerService {
    private BuyerRepository buyerRepository;

    public BuyerService() {
        buyerRepository = new BuyerRepository();
    }

    public Buyer save(Buyer buyer) {
        return buyerRepository.save(buyer);
    }

    public boolean existsByEmail(String email) {
        return buyerRepository.existsByEmail(email);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return buyerRepository.existsByPhoneNumber(phoneNumber);
    }

    public List<Product> findInterestsByBuyerId(Long buyerId) {
        List<Product> interests = buyerRepository.findInterestsByBuyerId(buyerId);
        return interests.subList(0, Math.min(interests.size(), 16));
    }
}
