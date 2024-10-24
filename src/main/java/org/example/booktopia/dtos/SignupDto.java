package org.example.booktopia.dtos;

import org.example.booktopia.model.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

public record SignupDto(String name, LocalDate dob, String job, String email, String password, BigDecimal creditLimit,
                        String phoneNumber, String country, String city, String street, String zipCode,
                        Set<Category> categories) {
}
