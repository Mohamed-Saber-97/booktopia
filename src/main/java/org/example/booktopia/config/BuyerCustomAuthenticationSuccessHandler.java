package org.example.booktopia.config;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.service.BuyerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.example.booktopia.utils.RequestAttributeUtil.*;

@Component
@RequiredArgsConstructor
public class BuyerCustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final BuyerService buyerService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String username = authentication.getName();
        if (authentication.getAuthorities()
                          .stream()
                          .anyMatch(a -> a.getAuthority()
                                          .equals("ROLE_BUYER"))) {
            BuyerDto buyer = buyerService.findByEmail(username);
            session.setAttribute(USER, buyer);
            session.setAttribute(BUYER, YES);
            session.setAttribute(PAGE_TITLE, "Home");
            request.setAttribute(SUCCESS, "Welcome back, %s".formatted(buyer.name()));
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.setAttribute(ERROR, "Invalid email or password");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/buyer-login.jsp");
            request.getSession()
                   .setAttribute(PAGE_TITLE, "Buyer login");
            dispatcher.forward(request, response);
        }
    }

}
