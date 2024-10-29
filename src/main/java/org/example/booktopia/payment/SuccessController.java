package org.example.booktopia.payment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class SuccessController {
    @GetMapping("/paymentSuccess")
    public String home(@RequestParam String amount, Model model) {
        model.addAttribute("amount", amount);
        return "payment-success";
    }
}
