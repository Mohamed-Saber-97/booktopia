package org.example.booktopia.viewcontrollers;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.booktopia.controller.PaymobController;
import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.dtos.OrderDto;
import org.example.booktopia.dtos.OrderProductDto;
import org.example.booktopia.model.Country;
import org.example.booktopia.payment.Request;
import org.example.booktopia.service.BuyerService;
import org.example.booktopia.service.CategoryService;
import org.example.booktopia.service.OrderProductService;
import org.example.booktopia.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

import static org.example.booktopia.utils.RequestAttributeUtil.*;

@Controller("BuyerController")
@RequestMapping("/buyers")
@RequiredArgsConstructor
public class BuyerController {

    private final BuyerService buyerService;
    private final CategoryService categoryService;
    private final UpdateUserSession updateUserSession;
    private final PaymobController paymobController;
    private final OrderService orderService;
    private final OrderProductService orderProductService;

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

    @GetMapping("/wishlist")
    public String getWishlist(HttpServletRequest request, Model model) {
        updateUserSession.updateUserSession(request);
        model.addAttribute(PAGE_TITLE, "Wishlist");
        return "wishlist";
    }

    @GetMapping("/orders")
    public String getOrders(HttpServletRequest request, Model model) {
        updateUserSession.updateUserSession(request);
        model.addAttribute(PAGE_TITLE, "Orders");
        List<OrderDto> orderDtos = buyerService.getOrdersByBuyerId(((BuyerDto) request.getSession().getAttribute(USER)).id());
        model.addAttribute(ORDERS, orderDtos);
        return "orders";
    }

    @GetMapping("/order-products")
    public String getOrderProducts(HttpServletRequest request, Model model, @RequestParam Long order) {
        updateUserSession.updateUserSession(request);
        model.addAttribute(PAGE_TITLE, "Order Products");
        Long buyerId = ((BuyerDto) request.getSession().getAttribute(USER)).id();
        List<OrderProductDto> orderProducts = orderProductService.findAllProductsByBuyerIdAndOrderId(buyerId, order);
        model.addAttribute(PRODUCTS, orderProducts);
        return "order-products";

    }

    @PostMapping("/update-cart")
    public String updateCart(HttpServletRequest request, @RequestParam("action") String action) {

        if ("credit".equals(action)) {
        }
        else if ("stripe".equals(action)) {
            String email = ((BuyerDto) request.getSession().getAttribute("user")).email();
            String totalBill = request.getParameter("grandTotal");
            BigDecimal totalBillInteger = new BigDecimal(Double.parseDouble(totalBill));

            Request stripeRequest = new Request();
            stripeRequest.setProductName("");
            stripeRequest.setEmail(email);
            stripeRequest.setAmount(totalBillInteger);

            request.setAttribute("stripeRequest" , stripeRequest);

            return "forward:/buyers/payment";
        }
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


