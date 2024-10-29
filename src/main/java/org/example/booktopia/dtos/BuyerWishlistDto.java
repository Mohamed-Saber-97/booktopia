package org.example.booktopia.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BuyerWishlistDto(
        Long buyerId,
        Long productId,
        String productName,
        String productDescription,
        String productAuthor,
        String productIsbn,
        LocalDate productReleaseDate,
        Long productCategoryId,
        String productImagePath,
        BigDecimal productPrice
) {
}
