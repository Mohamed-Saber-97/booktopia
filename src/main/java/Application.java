import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import repository.BuyerRepository;
import model.*;


public class Application {
    public static void main(String[] args) {


        EntityManagerFactory entityManagerFactory  = Persistence.createEntityManagerFactory("booktopia");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        BuyerRepository buyerRepository = new BuyerRepository();

        Buyer b = entityManager.find(Buyer.class, 2);
        Buyer b2 = entityManager.find(Buyer.class, 4);
        Product p = entityManager.find(Product.class, 1);
        Product p2 = entityManager.find(Product.class, 28);
        Product p3 = entityManager.find(Product.class, 51);


        buyerRepository.addToWishlist(b, p);
        buyerRepository.removeFromWishlist(b,p2);



    }
}
