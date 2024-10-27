package org.example.booktopia.viewcontrollers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.dtos.LoginDto;
import org.example.booktopia.dtos.SignupDto;
import org.example.booktopia.error.InvalidLoginCredentialsException;
import org.example.booktopia.model.Country;
import org.example.booktopia.service.BuyerService;
import org.example.booktopia.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.example.booktopia.utils.RequestAttributeUtil.*;

@Controller("BuyerController")
@RequestMapping("/buyers")
@RequiredArgsConstructor
public class BuyerController {

    private final BuyerService buyerService;
    private final CategoryService categoryService;
    private final UpdateUserSession updateUserSession;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute(PAGE_TITLE, "Buyer Login");
        return "buyer-login";
    }

    @GetMapping("/signup")
    public String signup(Model model, HttpSession session) {
        session.setAttribute(PAGE_TITLE, "Sign up");
        session.setAttribute(COUNTRIES, Country.countrySet);
        session.setAttribute(CATEGORIES, categoryService.findAllAvailableCategories());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(HttpServletRequest request, Model model, HttpSession session) {
        BuyerDto buyerDto = buyerService.save(request);
        session.setAttribute(USER, buyerDto);
        session.setAttribute(BUYER, YES);
        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCart(HttpServletRequest request, Model model) {
        updateUserSession.updateUserSession(request);
        model.addAttribute(PAGE_TITLE, "Cart");
        return "cart";
    }
}
