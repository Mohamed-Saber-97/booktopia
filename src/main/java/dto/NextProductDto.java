package dto;

import java.math.BigDecimal;

public record NextProductDto(Long id, String name, String imagePath, BigDecimal price, String author,
                             Integer quantity) {
}
