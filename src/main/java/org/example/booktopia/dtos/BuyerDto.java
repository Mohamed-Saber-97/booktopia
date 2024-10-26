package org.example.booktopia.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BuyerDto(
        Long id,
        String name,
        String email,
        LocalDate dob,
        String job,
        String phoneNumber,
        String country,
        String city,
        String street,
        String zipcode,
        Integer cartSize,
        Integer wishlistSize,
        BigDecimal creditLimit) {
}
