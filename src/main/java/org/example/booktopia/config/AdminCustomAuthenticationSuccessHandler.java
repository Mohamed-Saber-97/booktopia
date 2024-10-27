package org.example.booktopia.config;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.booktopia.service.AdminService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.example.booktopia.utils.RequestAttributeUtil.*;

@Component
@RequiredArgsConstructor
public class AdminCustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final AdminService adminService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String username = authentication.getName();
        if (authentication.getAuthorities()
                          .stream()
                          .anyMatch(a -> a.getAuthority()
                                          .equals("ROLE_ADMIN"))) {
            var admin = adminService.findByEmail(username);
            session.setAttribute(USER, admin);
            session.setAttribute(ADMIN, YES);
            session.setAttribute(PAGE_TITLE, "Home");
            request.setAttribute(SUCCESS, "Welcome back, %s".formatted(admin.name()));
            response.sendRedirect(request.getContextPath() + "/");
        } else {
            request.setAttribute(ERROR, "Invalid email or password");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin-login.jsp");
            request.getSession()
                   .setAttribute(PAGE_TITLE, "Admin login");
            dispatcher.forward(request, response);
        }
    }

}
