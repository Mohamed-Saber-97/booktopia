package repository;

import base.BaseRepository;
import model.Buyer;
import model.Order;
import model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BuyerRepository extends BaseRepository<Buyer, Long> {
    public BuyerRepository() {
        super(Buyer.class);
    }

    public List<Order> findOrdersByBuyerId(Long buyerId) {
        Buyer buyer = findById(buyerId).orElse(null);
        if (buyer != null) {
            return new ArrayList<>(buyer.getOrders());
        }
        return Collections.emptyList();
    }

    public boolean existsByEmail(String email) {
        return entityManager.createQuery("SELECT COUNT(b) FROM Buyer b WHERE b.account.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult() > 0;
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return entityManager.createQuery("SELECT COUNT(b) FROM Buyer b WHERE b.account.phoneNumber = :phoneNumber", Long.class)
                .setParameter("phoneNumber", phoneNumber)
                .getSingleResult() > 0;
    }

    public List<Product> findInterestsByBuyerId(Long buyerId) {
        Buyer buyer = findById(buyerId).orElse(null);
        if (buyer != null) {
            List<Product> interests = new ArrayList<>();
            buyer.getInterests().forEach(category -> interests.addAll(category.getProducts()));
            return interests;
        }
        return Collections.emptyList();
    }
}
