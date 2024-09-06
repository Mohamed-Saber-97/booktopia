package controller.viewcontroller;


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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String productId = request.getParameter("productId");
//        String quantity = request.getParameter("quantity");
//        PrintWriter out = response.getWriter();
//        if (!NotEmptyValidator.isValid(productId, quantity)) {
//            out.println("Invalid input");
//        } else {
//            Long id = Long.parseLong(productId);
//            int qty = Integer.parseInt(quantity);
//            // check if product exists
//            Product product = productController.findById(id);
//            if (product == null) {
//                out.println("Product not found");
//            } else if (qty <= 0 || qty > product.getQuantity()) {
//                out.println("Invalid quantity");
//            } else if (((Buyer) request.getSession().getAttribute("user")).getCart().containsKey(product)) {
//                ((Buyer) request.getSession().getAttribute("user")).getCart().remove(product);
//                out.println("removed");
//            } else {
//                ((Buyer) request.getSession().getAttribute("user")).getCart().put(product, qty);
//                out.println("added");
//            }
//        }
    }

    @Override
    public void init() {
        productController = new ProductController();
    }
}
