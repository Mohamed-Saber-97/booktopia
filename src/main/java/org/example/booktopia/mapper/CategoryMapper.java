package org.example.booktopia.mapper;

import org.example.booktopia.dtos.CategoryDto;
import org.example.booktopia.model.Category;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    Category toEntity(CategoryDto categoryDto);

    CategoryDto toDto(Category category);

    List<Category> toEntity(List<CategoryDto> categoryDtos);

    List<CategoryDto> toDto(List<Category> categories);

    Set<Category> toEntity(Set<CategoryDto> categoryDtos);

    Set<CategoryDto> toDto(Set<Category> categories);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(
            CategoryDto categoryDto, @MappingTarget Category category);
}
