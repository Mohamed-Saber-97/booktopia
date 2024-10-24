package org.example.booktopia.dtos;

public record OrderDto(
        Long id,
        String status,
        Long buyerId
) {
}
