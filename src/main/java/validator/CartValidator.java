package validator;

import java.util.List;
import java.util.Map;

import controller.ProductController;
import model.Buyer;
import model.Product;

public class CartValidator {

    private CartValidator() {
    }

    public static void validate(Map<Product, Integer> cart, Buyer buyer) {
        List<Product> availableProducts = new ProductController().findAllAvailable();
        for (Map.Entry entry : cart.entrySet()) {
            if (!availableProducts.contains((Product) entry.getKey())) {
                cart.remove((Product) entry.getKey());
                buyer.removeFromCart((Product) entry.getKey());
            } else if ((Integer) entry.getValue() > availableProducts.get(availableProducts.indexOf((Product) entry.getKey())).getQuantity()) {
                cart.remove((Product) entry.getKey());
                buyer.removeFromCart((Product) entry.getKey());
            }
        }
    }
}
