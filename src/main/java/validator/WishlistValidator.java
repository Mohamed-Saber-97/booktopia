package validator;

import model.Buyer;
import service.BuyerService;

public class WishlistValidator {
    public static final String ERROR_MESSAGE = "Some products are no longer available, please retry again";
    private WishlistValidator()
    {

    }
    public static boolean isValid(Buyer buyer) {
        return new BuyerService().validateAndUpdateWishList(buyer);
    }
}
