package org.example.booktopia.dtos;

import java.time.LocalDate;

public record OrderDto(
        Long id,
        String status,
        Long buyerId,
        LocalDate createdDate,
        Integer numberOfProducts
) {
}
