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

@WebServlet(value = "/addToWishlist")
public class AddToWishlistViewController extends HttpServlet {
    private ProductController productController;
    private BuyerController buyerController;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");
        PrintWriter out = response.getWriter();
        Buyer buyer = (Buyer) request.getSession().getAttribute("user");
        if (!NotEmptyValidator.isValid(productId)) {
            out.println("Invalid input");
        } else {
            Long id = Long.parseLong(productId);
            Product product = productController.findById(id);
            if (product == null) {
                out.println("Product not found");
            } else if (buyer.getWishlist().contains(product)) {
                buyer = buyerController.removeProductFromBuyerWishlist(buyer, product);
                request.getSession().setAttribute(USER, buyer);
                out.println("Product removed from wishlist");
            } else {
                buyerController.addProductToBuyerWishlist(buyer, product);
                request.getSession().setAttribute(USER, buyer);
                out.println("Product added to wishlist");
            }
        }
    }

    @Override
    public void init() {
        productController = new ProductController();
        buyerController = new BuyerController();
    }
}
