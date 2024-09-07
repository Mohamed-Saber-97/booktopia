package controller.viewcontroller;

import controller.ProductController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

import java.io.IOException;

@WebServlet(value = "/product")
public class SingleProductViewController extends HttpServlet {
    private ProductController productController;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productId = request.getParameter("p");
        if (productId == null || productId.isEmpty() || !productId.matches("\\d+")) {
            response.sendRedirect("/products");
        } else {
            Long id = Long.parseLong(productId);
            Product product = productController.findById(id);
            if (product == null) {
                response.sendRedirect("/products");
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("product-details.jsp");
                request.getSession().setAttribute("pageTitle", product.getName());
                request.getSession().setAttribute("product", product);
                dispatcher.forward(request, response);
            }
        }
    }

    @Override
    public void init() {
        productController = new ProductController();
    }
}
