package org.example.booktopia.payment;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppController {
    @Value("${stripe.api.publicKey}")
    private String publicKey;

    @PostMapping("/buyers/payment")
    public String showCard(HttpServletRequest request,
                           Model model) {
//        if (bindingResult.hasErrors()) {
//            bindingResult.getAllErrors()
//                         .forEach(System.out::println);
//        }
        Request stripeRequest = (Request) request.getAttribute("stripeRequest");

        model.addAttribute("publicKey", publicKey);
        model.addAttribute("amount", "%.2f".formatted(stripeRequest.getAmount()));
        model.addAttribute("email", stripeRequest.getEmail());
        model.addAttribute("productName", stripeRequest.getProductName());

        return "stripe";
    }
}
