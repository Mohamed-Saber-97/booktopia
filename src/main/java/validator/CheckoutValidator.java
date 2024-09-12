package validator;

import model.Buyer;
import service.BuyerService;

public class CheckoutValidator {
    public static String ERROR_MESSAGE = "Some products are no longer available, please retry again";

    private CheckoutValidator() {
    }

    public static boolean isValid(Buyer buyer) {
        return new BuyerService().validateAndUpdateCart(buyer);
    }
}
