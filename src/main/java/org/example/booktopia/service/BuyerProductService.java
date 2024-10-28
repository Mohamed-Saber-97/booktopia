package org.example.booktopia.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.dtos.CartItemDto;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.error.InsufficientFunds;
import org.example.booktopia.error.InsufficientStock;
import org.example.booktopia.mapper.BuyerMapper;
import org.example.booktopia.mapper.CartItemMapper;
import org.example.booktopia.mapper.ProductMapper;
import org.example.booktopia.model.Buyer;
import org.example.booktopia.model.Product;
import org.example.booktopia.repository.BuyerRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuyerProductService {
    private final BuyerService buyerService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final BuyerInterestService buyerInterestService;
    private final CartItemService cartItemService;
    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final CartItemMapper cartItemMapper;
    private final ProductMapper productMapper;
    private final BuyerMapper buyerMapper;
    private final BuyerRepository buyerRepository;

    public List<ProductDto> getBuyerInterestedProducts(Long buyerId) {
        List<Long> categoryIds = buyerInterestService.findCategoryIdsByBuyerId(buyerId);
        List<Long> availableCategoryIds = categoryService.findAllAvailableCategoriesById(categoryIds);
        return productService.findAllProductsByCategoryIds(availableCategoryIds);
    }

    @Transactional
    public BuyerDto checkout(Long id, String action) throws InsufficientStock, InsufficientFunds {
        Buyer buyer = buyerService.findById(id);
        List<CartItemDto> cartItems = cartItemService.getCartItems(id);
        Map<Long, Integer> productQuantities = new HashMap<>(cartItems.size());
        //el mafrod yekon fi save hena lel order
        for (var cartItem : cartItems) {
            Product product = productMapper.toEntity(productService.findProductById(cartItem.productId()));
            Integer productStock = product.getQuantity();
            Integer productQuantity = cartItem.quantity();
            BigDecimal productPrice = product.getPrice();
            if (productStock < productQuantity) {
                throw new InsufficientStock();
            }
            product.setQuantity(productStock - productQuantity);
            product = productMapper.toEntity(productService.save(product));

            BigDecimal orderPrice = productPrice.multiply(new BigDecimal(productQuantity));
            BigDecimal buyerCreditLimit = buyer.getCreditLimit();
            if (orderPrice.compareTo(buyerCreditLimit) > 0 && action.equals("credit")) {
                throw new InsufficientFunds();
            }
            if (action.equals("credit"))
                buyer.setCreditLimit(buyerCreditLimit.subtract(orderPrice));
            buyer = buyerRepository.save(buyer);
            productQuantities.put(product.getId(), productQuantity);
        }
        orderService.save(id, productQuantities);
        cartItemService.clearCart(id);
        return buyerService.save(buyer);
    }
}
