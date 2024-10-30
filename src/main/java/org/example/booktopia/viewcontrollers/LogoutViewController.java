package org.example.booktopia.viewcontrollers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.booktopia.dtos.AdminDto;
import org.example.booktopia.dtos.BuyerDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

import static org.example.booktopia.utils.RequestAttributeUtil.*;

@Controller
@AllArgsConstructor
public class LogoutViewController {

    @GetMapping("/logout")
    public String logout(HttpSession session,
                         Model model,
                         HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");

        if (session.getAttribute(ADMIN) != null) {
            AdminDto adminDto = (AdminDto) session.getAttribute(USER);
            request.setAttribute(SUCCESS, "Thank you for managing the site, %s".formatted(adminDto.name()));
        }
        if (session.getAttribute(BUYER) != null) {
            BuyerDto buyerDto = (BuyerDto) session.getAttribute(USER);
            request.setAttribute(SUCCESS, "Thanks for stopping by, %s".formatted(buyerDto.name()));

        }
        session.invalidate();
        request.setAttribute(PAGE_TITLE, "Home");
        dispatcher.forward(request, response);
        return "redirect:/";
    }
}
