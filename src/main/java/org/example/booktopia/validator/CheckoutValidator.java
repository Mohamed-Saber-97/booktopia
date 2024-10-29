package org.example.booktopia.validator;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import org.example.booktopia.model.Buyer;
import org.example.booktopia.model.Product;
import org.example.booktopia.repository.BuyerRepository;
import org.example.booktopia.service.ProductService;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class CheckoutValidator {
    private final BuyerRepository buyerRepository;
    private final ProductService productService;

//    public boolean validateAndUpdateCart(Buyer buyer) {
//        if (buyer == null || buyer.getCart() == null) return false;
//        int initialCartSize = buyer.getCart().size();
//        EntityManager entityManager = EMFactory.getEMF("booktopia").createEntityManager();
//        EntityTransaction transaction = entityManager.getTransaction();
//        try {
//            transaction.begin();
//            Map<Product, Integer> currentCart = buyer.getCart();
//            Set<Long> productIds = currentCart.keySet().stream().map(Product::getId).collect(Collectors.toSet());
//            Map<Product, Integer> currentProducts = productService.findByIdsWithQuantities(productIds);
//            currentCart.entrySet().stream().filter(entry -> currentProducts.containsKey(entry.getKey())).forEach(entry -> currentProducts.put(entry.getKey(), entry.getValue()));
//            buyer.clearCart();
//            buyer.addCartItem(currentProducts);
//            buyerRepository.update(buyer);
//            entityManager.getTransaction().commit();
//            return buyer.getCart().size() == initialCartSize;
//        } catch (Exception e) {
//            return false;
//        }
//        return false;
//    }
}
