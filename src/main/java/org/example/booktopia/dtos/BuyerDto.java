package org.example.booktopia.dtos;

import java.time.LocalDate;

public record BuyerDto(
        Long id,
        String name,
        String email,
        LocalDate birthday,
        String job,
        String phoneNumber,
        String country,
        String city,
        String street,
        String zipcode,
        Integer cartSize,
        Integer wishlistSize
) {
}
