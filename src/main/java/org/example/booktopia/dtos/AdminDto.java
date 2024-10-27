package org.example.booktopia.dtos;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link org.example.booktopia.model.Admin}
 */
public record AdminDto(Long id, String name, LocalDate dob, String password, String job, String email,
                       String phoneNumber, String street, String city, String zipCode,
                       String country) implements Serializable {
}