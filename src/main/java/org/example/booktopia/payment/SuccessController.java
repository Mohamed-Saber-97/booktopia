package org.example.booktopia.payment;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.error.InsufficientFunds;
import org.example.booktopia.error.InsufficientStock;
import org.example.booktopia.service.BuyerProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.example.booktopia.utils.RequestAttributeUtil.*;

@Controller
@RequiredArgsConstructor
public class SuccessController {
    private final BuyerProductService buyerProductService;

    @GetMapping("/paymentSuccess")
    public String home(@RequestParam String amount, Model model, HttpSession session, HttpServletRequest request,
                       HttpServletResponse response) {
        model.addAttribute("amount", amount);
        BuyerDto buyerDto = (BuyerDto) session.getAttribute(USER);
        try {
            buyerDto = buyerProductService.checkout(buyerDto.id(), "stripe");
            session.setAttribute(USER, buyerDto);
            session.setAttribute(SUCCESS, "Thank you for your purchase!");
        } catch (InsufficientStock | InsufficientFunds e) {
            request.getSession().setAttribute(ERROR, e.getMessage());
            session.setAttribute(ERROR, "Payment was not successful. Please try again");
            return "redirect:/buyers/cart";
        }
        return "payment-success";
    }

    @GetMapping("/paymentFail")
    public String paymentFail(HttpSession session) {
        session.setAttribute(ERROR, "Payment was not successful. Please try again");
        return "redirect:/buyers/cart";
    }
}
