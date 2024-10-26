package org.example.booktopia.config;

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
        }
//        else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_BUYER"))) {
//            var user = buyerService.loadUserByUsername(username); // This returns Buyer entity
//            session.setAttribute(USER, user);
//            session.setAttribute(BUYER, YES);
//        }
        response.sendRedirect(request.getContextPath() + "/");
    }

}
