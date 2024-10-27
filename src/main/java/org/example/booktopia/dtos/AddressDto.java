package org.example.booktopia.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link org.example.booktopia.model.Address}
 */
public record AddressDto(@Size(message = "Street name must be up to 255 characters",
                               max = 255) @NotBlank(message = "Street is required") String street,
                         @Size(message = "City name must be up to 100 characters",
                               max = 100) @NotBlank(message = "City is required") String city,
                         @Size(message = "Zipcode must be up to 15 characters",
                               max = 15) @NotBlank(message = "Zipcode is required") String zipcode,
                         @Size(message = "Country name must be up to 100 characters",
                               max = 100) @NotBlank(message = "Country is required") String country) implements Serializable {
}