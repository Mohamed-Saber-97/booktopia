package validator;

import model.Buyer;
import service.BuyerService;

public class CartValidator {
    public static final String ERROR_MESSAGE = "Some products are no longer available, please retry again";

    private CartValidator() {
    }

    public static boolean isValid(Buyer buyer) {
        return new BuyerService().validateAndUpdateCart(buyer);
    }
}
