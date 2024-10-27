package org.example.booktopia.config;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.example.booktopia.utils.RequestAttributeUtil.ERROR;
import static org.example.booktopia.utils.RequestAttributeUtil.PAGE_TITLE;

@Component
@RequiredArgsConstructor
public class BuyerCustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        request.setAttribute(ERROR, "Invalid email or password");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/buyer-login.jsp");
        request.getSession()
               .setAttribute(PAGE_TITLE, "Buyer login");
        dispatcher.forward(request, response);
    }
}
