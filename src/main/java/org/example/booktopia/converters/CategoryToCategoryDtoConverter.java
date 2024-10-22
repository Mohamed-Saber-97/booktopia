package org.example.booktopia.converters;

import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.model.Category;

public class CategoryToCategoryDtoConverter {
    public static CategoryDto convert(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }
}
