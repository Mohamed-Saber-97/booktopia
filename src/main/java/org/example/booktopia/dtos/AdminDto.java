package org.example.booktopia.dtos;

import java.io.Serializable;

/**
 * DTO for {@link org.example.booktopia.model.Admin}
 */
public record AdminDto(String accountName, String accountEmail, String accountPhoneNumber) implements Serializable {
  }