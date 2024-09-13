package controller.viewcontroller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import utils.Jsonify;

import java.io.IOException;
import java.util.List;

@WebServlet(value = "/next-products")
public class NextProductsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = new SearchProductsController().searchProducts(request);
        String jsonProducts = Jsonify.jsonifyProducts(products);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"products\":" + jsonProducts + "}");
    }
}
