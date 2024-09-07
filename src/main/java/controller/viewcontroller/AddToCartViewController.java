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

@WebServlet(value = "/addToCart")
public class AddToCartViewController extends HttpServlet {
    private ProductController productController;
    private BuyerController buyerController;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("productId");
        String quantity = request.getParameter("quantity");
        PrintWriter out = response.getWriter();
        Buyer buyer = (Buyer) request.getSession().getAttribute("user");
        if (!NotEmptyValidator.isValid(productId, quantity)) {
            out.println("Invalid input");
        } else {
            Long id = Long.parseLong(productId);
            int qty = Integer.parseInt(quantity);
            Product product = productController.findById(id);
            if (product == null) {
                out.println("Product not found");
            } else if (buyer.getCart().containsKey(product)) {
                buyerController.removeProductFromCart(buyer, product);
                out.println("Product removed from cart");
            } else if (qty <= 0 || qty > product.getQuantity()) {
                out.println("Invalid quantity");
            } else {
                buyerController.addProductToBuyerCart(buyer, product, qty);
                out.println("Product added to cart");
            }
        }
    }

    @Override
    public void init() {
        productController = new ProductController();
        buyerController = new BuyerController();
    }
}
