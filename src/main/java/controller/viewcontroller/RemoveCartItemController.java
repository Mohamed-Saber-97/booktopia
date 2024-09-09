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
import java.io.PrintWriter;

import static utils.RequestAttributeUtil.USER;

@WebServlet(value = "/remove-cart-item")
public class RemoveCartItemController extends HttpServlet {
    private ProductController productController;
    private BuyerController buyerController;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");
        PrintWriter out = response.getWriter();
        if (!NotEmptyValidator.isValid(productId) || !productId.matches("\\d+")) {
            out.print("Invalid input");
        } else {
            Long id = Long.parseLong(productId);
            Product product = productController.findAvailableProductById(id);
            if (product != null) {
                Buyer buyer = (Buyer) request.getSession().getAttribute(USER);
                buyerController.removeProductFromCart(buyer, product);
                out.print("success");
            } else {
                out.print("Product not found");
            }
        }
    }

    @Override
    public void init() {
        productController = new ProductController();
        buyerController = new BuyerController();
    }
}
