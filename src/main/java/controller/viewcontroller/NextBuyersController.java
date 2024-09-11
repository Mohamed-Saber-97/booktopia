package controller.viewcontroller;

import com.google.gson.Gson;
import controller.BuyerController;
import dto.BuyerDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mapper.BuyerToBuyerDto;
import model.Buyer;
import validator.NotEmptyValidator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/next-buyers")
public class NextBuyersController extends HttpServlet {
    private BuyerController buyerController;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageNumberString = request.getParameter("page");
        int pageNumber = NotEmptyValidator.isValid(pageNumberString) ? Integer.parseInt(pageNumberString) : 0;
        List<Buyer> buyers = buyerController.search(pageNumber, 16);

        List<BuyerDto> buyerDtos = new ArrayList<>();
        BuyerToBuyerDto buyerToBuyerDto = new BuyerToBuyerDto();
        for (int i = 0; i < buyers.size(); i++) {
            buyerDtos.add(buyerToBuyerDto.convert(buyers.get(i)));
        }
        Gson gson = new Gson();
        String jsonBuyers = gson.toJson(buyerDtos);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"buyers\":" + jsonBuyers + "}");
    }

    @Override
    public void init() {
        buyerController = new BuyerController();
    }
}
