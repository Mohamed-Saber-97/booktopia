package validator;

import java.util.List;
import java.util.Map;

import controller.BuyerController;
import controller.ProductController;
import model.Buyer;
import model.Product;

public class CartValidator {
    public static final String ERROR_MESSAGE = "Some products are no longer available, please retry again";

    private CartValidator() {
    }

    public static boolean isValid(Map<Product, Integer> cart, Buyer buyer) {
        boolean isValid = true;
        ProductController productController = new ProductController();
        BuyerController buyerController = new BuyerController();
        List<Product> availableProducts = productController.findAllAvailable();
        for (Map.Entry entry : cart.entrySet()) {
            if (!availableProducts.contains((Product) entry.getKey())) {
                isValid = false;
                cart.remove((Product) entry.getKey());
                buyer.removeFromCart((Product) entry.getKey());
            } else if ((Integer) entry.getValue() > availableProducts.get(availableProducts.indexOf((Product) entry.getKey())).getQuantity()) {
                isValid = false;
                buyerController.setBuyerCartProductQuantity(buyer, (Product) entry.getKey(), 1);
            }
        }
        return isValid;
    }
}
