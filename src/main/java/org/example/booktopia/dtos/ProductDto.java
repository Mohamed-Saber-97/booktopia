package org.example.booktopia.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductDto(
        Long id,
        String name,
        String description,
        String author,
        String isbn,
        LocalDate releaseDate,
        BigDecimal price,
        Integer quantity,
        String imagePath,
        CategoryDto category
) {
}
