package controller.viewcontroller;

import controller.BuyerController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Buyer;

import java.io.IOException;
import java.util.List;

import static utils.RequestAttributeUtil.BUYERS;
import static utils.RequestAttributeUtil.PAGE_TITLE;

@WebServlet(value = "/buyers")
public class BuyersViewController extends HttpServlet {
    private BuyerController buyerController;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Buyer> buyers = buyerController.search(0, 16);
        RequestDispatcher dispatcher = request.getRequestDispatcher("buyers.jsp");
        request.getSession().setAttribute(PAGE_TITLE, "Buyers");
        request.getSession().setAttribute(BUYERS, buyers);
        dispatcher.forward(request, response);
    }

    @Override
    public void init() {
        buyerController = new BuyerController();
    }
}
