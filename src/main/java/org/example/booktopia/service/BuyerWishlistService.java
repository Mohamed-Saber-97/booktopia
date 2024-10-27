package org.example.booktopia.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.booktopia.dtos.BuyerWishlistDto;
import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.error.RecordNotFoundException;
import org.example.booktopia.mapper.BuyerWishlistMapper;
import org.example.booktopia.mapper.ProductMapper;
import org.example.booktopia.model.Buyer;
import org.example.booktopia.model.BuyerWishlist;
import org.example.booktopia.model.BuyerWishlistId;
import org.example.booktopia.model.Product;
import org.example.booktopia.repository.BuyerWishlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BuyerWishlistService {
    private final BuyerWishlistRepository buyerWishlistRepository;
    private final BuyerService buyerService;
    private final ProductService productService;
    private final ProductMapper productMapper;
    private final BuyerWishlistMapper buyerWishlistMapper;

    @Transactional
    public String addProductToWishlist(Long buyerId, Long productId) {
        if (buyerWishlistRepository.existsByBuyerIdAndProductId(buyerId, productId)) {
            return this.removeProductFromWishlist(buyerId, productId);
        }
        Buyer buyer = buyerService.findById(buyerId);
        ProductDto productDto = productService.findProductById(productId);
        Product product = productMapper.toEntity(productDto);
        BuyerWishlist buyerWishlist = new BuyerWishlist();
        buyerWishlist.setId(new BuyerWishlistId(buyerId, productId));
        buyerWishlist.setBuyer(buyer);
        buyerWishlist.setProduct(product);
        buyerWishlistRepository.save(buyerWishlist);
        log.info("Product {} added to Buyer {}'s wishlist", productId, buyerId);
        return "Product added to wishlist";
    }

    @Transactional
    public String removeProductFromWishlist(Long buyerId, Long productId) {
        if (!buyerWishlistRepository.existsByBuyerIdAndProductId(buyerId, productId)) {
            System.out.println("Not found");
            throw new RecordNotFoundException("Product", "ID", productId.toString());
//            return this.addProductToWishlist(buyerId, productId);
        }
        System.out.println("Found in wishlist");
        buyerWishlistRepository.removeProductFromWishlist(buyerId, productId);
        log.info("Product {} removed from Buyer {}'s wishlist", productId, buyerId);
        return "Product removed from wishlist";
    }

    public List<BuyerWishlistDto> getBuyerWishlist(Long buyerId) {
        List<BuyerWishlist> buyerWishlists = buyerWishlistRepository.findByBuyer_id(buyerId);
        return buyerWishlistMapper.toDto(buyerWishlists);
    }
}
