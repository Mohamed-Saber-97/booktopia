package org.example.booktopia.dtos;

import java.io.Serializable;

public record CategoryDto(Long id, String name) implements Serializable {
}
