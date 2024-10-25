package org.example.booktopia.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CartItemDto(
        Long buyerId,
        Long productId,
        Integer quantity,
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
