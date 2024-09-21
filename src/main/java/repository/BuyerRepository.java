package repository;

import base.BaseRepository;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import model.Buyer;
import model.Order;
import model.Product;

import java.util.Collections;
import java.util.List;

import static utils.RequestParameterUtil.IS_DELETED;

public class BuyerRepository extends BaseRepository<Buyer, Long> {
    public BuyerRepository() {
        super(Buyer.class);
    }

    public boolean existsByEmail(String email) {
        EntityManager entityManager = getEntityManager();
        boolean result = entityManager.createQuery("SELECT COUNT(b) FROM Buyer b WHERE b.account.email = :email", Long.class).setParameter("email", email).getSingleResult() > 0;
        closeEntityManager(entityManager);
        return result;
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        EntityManager entityManager = getEntityManager();
        boolean result = entityManager.createQuery("SELECT COUNT(b) FROM Buyer b WHERE b.account.phoneNumber = :phoneNumber", Long.class).setParameter("phoneNumber", phoneNumber).getSingleResult() > 0;
        closeEntityManager(entityManager);
        return result;
    }

    public Buyer findByPhoneNumber(String phoneNumber) {
        List<Buyer> result = findByField("account.phone_number", phoneNumber);
        return result.isEmpty() ? null : result.getFirst();
    }

    public Buyer findByEmail(String email) {
        List<Buyer> result = findByField("account.email", email);
        return result.isEmpty() ? null : result.getFirst();
    }

    public List<Product> findInterestsByBuyerId(Long buyerId) {
        EntityManager entityManager = getEntityManager();
        Buyer buyer = findById(buyerId).orElse(null);
        if (buyer != null) {
            TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p JOIN Category c ON p MEMBER OF c.products JOIN Buyer b ON c MEMBER OF b.interests WHERE b.id = :buyerId AND p.isDeleted = false AND c.isDeleted = false", Product.class);
            query.setParameter("buyerId", buyerId);
            List<Product> result = query.getResultList();
            closeEntityManager(entityManager);
            return result;
        }
        closeEntityManager(entityManager);
        return Collections.emptyList();
    }


    public void addProductToCart(Buyer buyer, Product product, int quantity) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            buyer = findById(buyer.getId()).orElse(null);
            buyer = entityManager.merge(buyer);
            buyer.addCartItem(product, quantity);
            transaction.commit();
            closeEntityManager(entityManager);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            closeEntityManager(entityManager);
            throw e;
        }
    }

    public void removeProductFromCart(Buyer buyer, Product product) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            buyer = findById(buyer.getId()).orElse(null);
            buyer = entityManager.merge(buyer);
            buyer.removeFromCart(product);
            transaction.commit();
            closeEntityManager(entityManager);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            closeEntityManager(entityManager);
            throw e;
        }
    }

    public int incrementProductQuantity(Buyer buyer, Product product) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            buyer = findById(buyer.getId()).orElse(null);
            buyer = entityManager.merge(buyer);
            int qty = buyer.addToCart(product, 1);
            transaction.commit();
            closeEntityManager(entityManager);
            return qty;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            closeEntityManager(entityManager);
            throw e;
        }
    }

    public int decrementProductQuantity(Buyer buyer, Product product) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            buyer = findById(buyer.getId()).orElse(null);
            buyer = entityManager.merge(buyer);
            int currentQuantity = buyer.getCart().getOrDefault(product, 0);
            int qty = 1;
            if (currentQuantity > 1) {
                qty = buyer.addToCart(product, -1);
//                buyer = entityManager.merge(buyer);
            }
            transaction.commit();
            closeEntityManager(entityManager);
            return qty;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            closeEntityManager(entityManager);
            throw e;
        }
    }

    public void clearCart(Buyer buyer) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            buyer = findById(buyer.getId()).orElse(null);
            buyer = entityManager.merge(buyer);
            buyer.clearCart();
            transaction.commit();
            closeEntityManager(entityManager);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            closeEntityManager(entityManager);
            throw e;
        }
    }

    public void setProductQuantity(Buyer buyer, Product product, int quantity) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            buyer = findById(buyer.getId()).orElse(null);
            buyer = entityManager.merge(buyer);
            if (quantity > 0) {
                buyer.getCart().put(product, quantity);
            } else {
                buyer.getCart().remove(product);
            }
//            update(buyer);
            transaction.commit();
            closeEntityManager(entityManager);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            closeEntityManager(entityManager);
            throw e;
        }
    }

    public void addToWishlist(Buyer buyer, Product product) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            buyer = findById(buyer.getId()).orElse(null);
            buyer = entityManager.merge(buyer);
            buyer.addToWishlist(product);
            transaction.commit();
            closeEntityManager(entityManager);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            closeEntityManager(entityManager);
            throw e;
        }
    }

    public void removeFromWishlist(Buyer buyer, Product product) {
        EntityManager entityManager = getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            buyer = findById(buyer.getId()).orElse(null);
            buyer = entityManager.merge(buyer);
            buyer.removeFromWishlist(product);
            transaction.commit();
            closeEntityManager(entityManager);
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            closeEntityManager(entityManager);
            throw e;
        }
    }

    public List<Buyer> search(int pageNumber, int pageSize) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Buyer> query = cb.createQuery(Buyer.class);
        Root<Buyer> buyerRoot = query.from(Buyer.class);
        Predicate predicate = cb.equal(buyerRoot.get(IS_DELETED), false);
        query.select(buyerRoot).where(predicate);
        List<Buyer> result = entityManager.createQuery(query).setFirstResult(pageNumber * pageSize).setMaxResults(pageSize).getResultList();
        closeEntityManager(entityManager);
        return result;
    }

    public List<Order> searchOrders(Long buyerId, int pageNumber, int pageSize) {
        EntityManager entityManager = getEntityManager();
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> orderRoot = query.from(Order.class);
        Predicate predicate = cb.equal(orderRoot.get("buyer").get("id"), buyerId);
        predicate = cb.and(predicate, cb.equal(orderRoot.get(IS_DELETED), false));
        query.select(orderRoot).where(predicate);
        List<Order> result = entityManager.createQuery(query).setFirstResult(pageNumber * pageSize).setMaxResults(pageSize).getResultList();
        closeEntityManager(entityManager);
        return result;
    }
}
