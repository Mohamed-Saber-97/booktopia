package controller.viewcontroller;

import controller.BuyerController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Buyer;
import utils.ValidatorUtil;

import java.io.IOException;
import java.util.Map;

import static utils.RequestAttributeUtil.ERROR;
import static utils.RequestAttributeUtil.PAGE_TITLE;

@WebServlet(value = "/buyer-profile")
public class BuyerProfileViewController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> errors = ValidatorUtil.validateEntityId(request, "p");
        if (!errors.isEmpty()) {
            request.setAttribute(ERROR, errors.get(ERROR));
            response.sendRedirect("/buyers");
        } else {
            Buyer buyer = new BuyerController().findById(Long.parseLong(request.getParameter("p")));
            if (buyer == null) {
                request.setAttribute(ERROR, "Buyer not found");
                response.sendRedirect("/buyers");
            } else {
                RequestDispatcher dispatcher = request.getRequestDispatcher("buyer-profile.jsp");
                request.getSession().setAttribute(PAGE_TITLE, "Buyer's Profile");
                request.getSession().setAttribute("tempBuyer", buyer);
                dispatcher.forward(request, response);
            }
        }
    }
}
