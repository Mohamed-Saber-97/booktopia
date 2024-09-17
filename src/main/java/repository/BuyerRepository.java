package repository;

import base.BaseRepository;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import model.Buyer;
import model.Order;
import model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static utils.RequestParameterUtil.*;

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
        return entityManager.createQuery("SELECT COUNT(b) FROM Buyer b WHERE b.account.email = :email", Long.class).setParameter("email", email).getSingleResult() > 0;
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return entityManager.createQuery("SELECT COUNT(b) FROM Buyer b WHERE b.account.phoneNumber = :phoneNumber", Long.class).setParameter("phoneNumber", phoneNumber).getSingleResult() > 0;
    }

    public Buyer findByPhoneNumber(String phoneNumber) {
        String jpql = "SELECT b FROM Buyer b WHERE b.account.phoneNumber = :phoneNumber and b.isDeleted = false";
        TypedQuery<Buyer> query = entityManager.createQuery(jpql, Buyer.class);
        Buyer result = null;
        try {
            result = query.setParameter(PHONE_NUMBER, phoneNumber).getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    public List<Product> findInterestsByBuyerId(Long buyerId) {
        entityManager.clear();
        Buyer buyer = findById(buyerId).orElse(null);
        if (buyer != null) {
            TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p JOIN Category c ON p MEMBER OF c.products JOIN Buyer b ON c MEMBER OF b.interests WHERE b.id = :buyerId AND p.isDeleted = false AND c.isDeleted = false", Product.class);
            query.setParameter("buyerId", buyerId);
            return query.getResultList();
        }
        return Collections.emptyList();
    }

    public Buyer findByEmail(String email) {
        String jpql = "SELECT b FROM Buyer b WHERE b.account.email = :email and b.isDeleted = false";
        TypedQuery<Buyer> query = entityManager.createQuery(jpql, Buyer.class);
        query.setParameter(EMAIL, email);
        return query.getSingleResult();
    }

    public void addProductToCart(Buyer buyer, Product product, int quantity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            buyer.addCartItem(product, quantity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void removeProductFromCart(Buyer buyer, Product product) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            buyer.removeFromCart(product);
            buyer = entityManager.merge(buyer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public int incrementProductQuantity(Buyer buyer, Product product) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            int qty = buyer.addToCart(product, 1);
            buyer = entityManager.merge(buyer);
            transaction.commit();
            return qty;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public int decrementProductQuantity(Buyer buyer, Product product) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            int currentQuantity = buyer.getCart().getOrDefault(product, 0);
            int qty = 1;
            if (currentQuantity > 1) {
                qty = buyer.addToCart(product, -1);
                buyer = entityManager.merge(buyer);
            }
            transaction.commit();
            return qty;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void clearCart(Buyer buyer) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            buyer.clearCart();
            buyer = entityManager.merge(buyer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void setProductQuantity(Buyer buyer, Product product, int quantity) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            if (quantity > 0) {
                buyer.getCart().put(product, quantity);
            } else {
                buyer.getCart().remove(product);
            }
            update(buyer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void addToWishlist(Buyer buyer, Product product) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            buyer.addToWishlist(product);
            buyer = entityManager.merge(buyer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public void removeFromWishlist(Buyer buyer, Product product) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            buyer.removeFromWishlist(product);
            buyer = entityManager.merge(buyer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
    }

    public List<Buyer> search(int pageNumber, int pageSize) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Buyer> query = cb.createQuery(Buyer.class);
        Root<Buyer> buyerRoot = query.from(Buyer.class);
        Predicate predicate = cb.equal(buyerRoot.get(IS_DELETED), false);
        query.select(buyerRoot).where(predicate);
        return entityManager.createQuery(query).setFirstResult(pageNumber * pageSize).setMaxResults(pageSize).getResultList();
    }

    public List<Order> searchOrders(Long buyerId, int pageNumber, int pageSize) {
        entityManager.clear();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> orderRoot = query.from(Order.class);
        Predicate predicate = cb.equal(orderRoot.get("buyer").get("id"), buyerId);
        predicate = cb.and(predicate, cb.equal(orderRoot.get(IS_DELETED), false));
        query.select(orderRoot).where(predicate);
        return entityManager.createQuery(query).setFirstResult(pageNumber * pageSize).setMaxResults(pageSize).getResultList();
    }
}
