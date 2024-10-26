package org.example.booktopia.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record NewProductDto(
        String name,
        String description,
        String author,
        String isbn,
        LocalDate releaseDate,
        BigDecimal price,
        Integer quantity,
        Long categoryId
) {
}
