package org.example.booktopia.dtos;

import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link org.example.booktopia.model.Account}
 */
public record AccountDto(@Size(message = "Name must be up to 100 characters", max = 100) @NotBlank(message = "Name is required") String name, @NotNull(message = "Birthday is required") LocalDate birthday, @Size(message = "Password must be at least 6 characters long", min = 6) @NotBlank(message = "Password is required") String password, @Size(message = "Job title must be up to 100 characters", max = 100) String job, @Email(message = "Invalid email format") @NotBlank(message = "Email is required") String email, @Pattern(message = "Invalid phone number format", regexp = "^01[0-25]\\d{8}$") @NotBlank(message = "Phone number is required") String phoneNumber, AddressDto address) implements Serializable {
  }