package org.example.booktopia.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.error.DuplicateRecordException;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.mapper.ProductMapper;
import org.example.booktopia.model.Buyer;
import org.example.booktopia.model.BuyerWishlist;
import org.example.booktopia.model.BuyerWishlistId;
import org.example.booktopia.model.Product;
import org.example.booktopia.repository.BuyerWishlistRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuyerWishlistService {
    private final BuyerWishlistRepository buyerWishlistRepository;
    private final BuyerService buyerService;
    private final ProductService productService;
    private final ProductMapper productMapper;

    @Transactional
    public void addProductToWishlist(Long buyerId, Long productId) {
        if (buyerWishlistRepository.existsByBuyerIdAndProductId(buyerId, productId))
            throw new DuplicateRecordException("Product", productId.toString());
        Buyer buyer = buyerService.findById(buyerId);
        ProductDto productDto = productService.findProductById(productId);
        Product product = productMapper.toEntity(productDto);
        BuyerWishlist buyerWishlist = new BuyerWishlist();
        buyerWishlist.setId(new BuyerWishlistId(buyerId, productId));
        buyerWishlist.setBuyer(buyer);
        buyerWishlist.setProduct(product);
        buyerWishlistRepository.save(buyerWishlist);
        log.info("Product {} added to Buyer {}'s wishlist", productId, buyerId);
    }

    @Transactional
    public void removeProductFromWishlist(Long buyerId, Long productId) {
        if (!buyerWishlistRepository.existsByBuyerIdAndProductId(buyerId, productId)) {
            throw new RecordNotFoundException("Product", "ID", productId.toString());
        }
        buyerWishlistRepository.removeProductFromWishlist(buyerId, productId);
        log.info("Product {} removed from Buyer {}'s wishlist", productId, buyerId);
    }

}
