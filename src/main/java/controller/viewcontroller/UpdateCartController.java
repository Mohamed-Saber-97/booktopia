package controller.viewcontroller;

import controller.BuyerController;
import controller.ProductController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Buyer;
import model.Product;
import validator.NotEmptyValidator;

import java.io.IOException;

import static utils.RequestAttributeUtil.USER;

@WebServlet(value = "/update-cart")
public class UpdateCartController extends HttpServlet {
    private ProductController productController;
    private BuyerController buyerController;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues("id");
        String[] quantities = request.getParameterValues("quantity");
        if (!NotEmptyValidator.isValid(productIds) || !NotEmptyValidator.isValid(quantities)) {
            response.sendRedirect("/cart");
        } else {
            Buyer buyer = (Buyer) request.getSession().getAttribute(USER);
            for (int i = 0; i < productIds.length; i++) {
                Long productId = Long.parseLong(productIds[i]);
                int quantity = Integer.parseInt(quantities[i]);
                Product product = productController.findAvailableProductById(productId);
                if (product != null) {
                    buyerController.setBuyerCartProductQuantity(buyer, product, quantity);
                } else {
                    Product oldProduct = productController.findById(productId);
                    buyerController.removeProductFromCart(buyer, oldProduct);
                }
            }
            response.sendRedirect("/cart");
        }
    }

    @Override
    public void init() {
        productController = new ProductController();
        buyerController = new BuyerController();
    }
}
