package org.example.booktopia.dtos;

import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProductDto(
        Long id,
        String name,
        String description,
        String author,
        String isbn,
        LocalDate releaseDate,
        Long categoryId,
        BigDecimal price,
        Integer quantity,
        String imagePath
) {
}
