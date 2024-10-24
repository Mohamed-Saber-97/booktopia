package org.example.booktopia.dtos;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link org.example.booktopia.model.Category}
 */
public record CategoryDto(Long id, @NotBlank(message = "name is required") String name) implements Serializable {
}