package repository;

import base.BaseRepository;
import jakarta.persistence.TypedQuery;
import model.Buyer;
import model.Category;
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
            TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p JOIN Category c ON p MEMBER OF c.products JOIN Buyer b ON c MEMBER OF b.interests WHERE b.id = :buyerId AND p.isDeleted = false AND c.isDeleted = false", Product.class);
            query.setParameter("buyerId", buyerId);
            return query.getResultList();
        }
        return Collections.emptyList();
    }
}
