package org.example.booktopia.dtos;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * DTO for {@link org.example.booktopia.model.Buyer}
 */
public record BuyerDto(Long id, String name, LocalDate dob, String password,
                       String job, String email, String phoneNumber, String street,
                       String city, String zipCode, String country, Integer cartSize,
                       Integer wishlistSize,
                       @NotNull BigDecimal creditLimit) implements Serializable {
}