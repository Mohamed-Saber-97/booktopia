package controller.viewcontroller;

import com.google.gson.Gson;
import dto.NextProductDto;
import dto.ProductDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mapper.ProductToNextProductDto;
import model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/next-products")
public class NextProductsController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = new SearchProductsController().searchProducts(request);

        List<NextProductDto> productDtos = new ArrayList<>();
        ProductToNextProductDto productToNextProductDto = new ProductToNextProductDto();
        for (int i = 0; i < products.size(); i++) {
            productDtos.add(productToNextProductDto.convert(products.get(i)));
        }
        Gson gson = new Gson();
        String jsonProducts = gson.toJson(productDtos);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"products\":" + jsonProducts + "}");
    }
}
