package org.example.booktopia.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.error.IllegalValueException;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.mapper.CategoryMapper;
import org.example.booktopia.mapper.ProductMapper;
import org.example.booktopia.model.*;
import org.example.booktopia.repository.CartItemRepository;
import org.example.booktopia.repository.CategoryRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    private final BuyerService buyerService;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @Transactional
    public void addCartItem(Long buyerId, Long productId, Integer quantity) {
        Buyer buyer = buyerService.findById(buyerId);
        System.out.println(buyer.getId());
        ProductDto productDto = productService.findProductById(productId);
        Product product = productMapper.toEntity(productDto);
        CartItemId cartItemId = new CartItemId(buyerId, productId);

        CartItem existingCartItem = cartItemRepository.findById(cartItemId)
                .orElse(null);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
            cartItemRepository.save(existingCartItem);
        } else {
            CategoryDto categoryDto = categoryService.findById(productDto.categoryId());
            Category category = categoryMapper.toEntity(categoryDto);
            product.setCategory(category);
            CartItem cartItem = new CartItem(buyer, product, quantity);
            cartItemRepository.save(cartItem);
        }
    }

    @Transactional
    public void removeCartItem(Long buyerId, Long productId) {
        CartItemId cartItemId = new CartItemId(buyerId, productId);
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new RecordNotFoundException("Cart Item", "ID", cartItemId.toString());
        }
        cartItemRepository.deleteById(cartItemId);
    }

    @Transactional
    public void incrementProductQuantity(Long buyerId, Long productId) {
        CartItemId cartItemId = new CartItemId(buyerId, productId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RecordNotFoundException("Cart Item", "ID",
                        cartItemId.toString()));
        cartItem.setQuantity(cartItem.getQuantity() + 1);
        cartItemRepository.save(cartItem);
    }


    @Transactional
    public void decrementProductQuantity(Long buyerId, Long productId) {
        CartItemId cartItemId = new CartItemId(buyerId, productId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RecordNotFoundException("Cart Item", "ID",
                        cartItemId.toString()));

        int newQuantity = cartItem.getQuantity() - 1;
        if (newQuantity < 1) {
            throw new IllegalValueException("Quantity", String.valueOf(newQuantity));
        }
        cartItem.setQuantity(newQuantity);
        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void clearCart(Long buyerId) {
        cartItemRepository.clearCartByBuyerId(buyerId);
        log.info("Cart cleared for Buyer with ID: {}", buyerId);
    }
}
