package org.example.booktopia.viewcontrollers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.booktopia.controller.PaymobController;
import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.dtos.LoginDto;
import org.example.booktopia.dtos.SignupDto;
import org.example.booktopia.error.InvalidLoginCredentialsException;
import org.example.booktopia.model.Country;
import org.example.booktopia.service.BuyerService;
import org.example.booktopia.service.CategoryService;
import org.springframework.http.ResponseEntity;
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
    private final PaymobController paymobController;

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

    @PostMapping("/update-cart")
    public String updateCart(HttpServletRequest request) {
        ResponseEntity<String> paymentKey = paymobController.checkout(145 * 100);
        if (paymentKey.hasBody()) {
            System.out.println("My keyeeee");
            System.out.println(paymentKey.getBody());
        }
//        return paymentKey.getBody();
        // redirect to iframe with paymentKey
        return "redirect:https://accept.paymob.com/api/acceptance/iframes/877560?payment_token=" + paymentKey.getBody();

//        Buyer buyer = (Buyer) request.getSession().getAttribute(USER);
//        if (!CheckoutValidator.isValid(buyer)) {
//            request.setAttribute(ERROR, CartValidator.ERROR_MESSAGE);
//        } else {
//            try {
//                buyer = new BuyerService().checkout(buyer);
//                request.getSession().setAttribute(USER, buyer);
//                request.getSession().setAttribute(SUCCESS, "Thank you for your purchase!");
//                response.sendRedirect(request.getContextPath() + "/");
//            } catch (InsufficientStock | InsufficientFunds e) {
//                request.getSession().setAttribute(ERROR, e.getMessage());
//                response.sendRedirect(request.getContextPath() + "/cart");
//            }
//        }
//        return "redirect:/";
    }
}


