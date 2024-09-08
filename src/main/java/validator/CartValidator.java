package validator;

import java.util.Iterator;
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
        Iterator<Map.Entry<Product, Integer>> iterator = cart.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<Product, Integer> entry = iterator.next();
            Product product = entry.getKey();

            if (!availableProducts.contains(entry.getKey())) {
                isValid = false;
                buyer.removeCartItem(product);
            } else if (entry.getValue() > availableProducts.get(availableProducts.indexOf(product)).getQuantity()) {
                isValid = false;
//                buyerController.setBuyerCartProductQuantity(buyer, product, 1);
            }
        }

        return isValid;
    }
}
