package org.example.booktopia.converters;

import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.model.Category;

public class CategoryDtoToCategoryConverter {
    public static Category convert(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.id());
        category.setName(categoryDto.name());
        return category;
    }
}
