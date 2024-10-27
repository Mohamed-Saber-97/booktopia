package org.example.booktopia.viewcontrollers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.dtos.BuyerWishlistDto;
import org.example.booktopia.dtos.CartItemDto;
import org.example.booktopia.mapper.BuyerMapper;
import org.example.booktopia.model.Buyer;
import org.example.booktopia.service.BuyerService;
import org.example.booktopia.service.BuyerWishlistService;
import org.example.booktopia.service.CartItemService;
import org.springframework.stereotype.Controller;

import java.util.List;

import static org.example.booktopia.utils.RequestAttributeUtil.*;

@Controller
@AllArgsConstructor
public class UpdateUserSession {
    private final BuyerService buyerService;
    private final CartItemService cartItemService;
    private final BuyerWishlistService buyerWishlistService;
    private final BuyerMapper buyerMapper;

    public void updateUserSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(BUYER) != null) {
            Long buyerId = ((BuyerDto) session.getAttribute(USER)).id();
            Buyer loggedBuyer = buyerService.findById(buyerId);
            BuyerDto buyer = buyerMapper.toDto(loggedBuyer);
            List<CartItemDto> cartItemDtos = cartItemService.getCartItems(buyerId);
            List<BuyerWishlistDto> wishlistDtos = buyerWishlistService.getBuyerWishlist(buyerId);
            List<Long> cart = cartItemDtos.stream().map(CartItemDto::productId).toList();
            List<Long> wishlist = wishlistDtos.stream().map(BuyerWishlistDto::productId).toList();
            session.setAttribute(CART, cartItemDtos);
            session.setAttribute(WISHLIST, wishlistDtos);
            session.setAttribute(USER, buyer);
        }
    }
}
