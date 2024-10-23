package org.example.booktopia.mapper;

import org.example.booktopia.dtos.CategoryDTO;
import org.example.booktopia.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.i18n.LocaleContextHolder;

@Mapper(imports = {LocaleContextHolder.class})
public interface CategoryMapper extends GenericMapper<Category, CategoryDTO> {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
}
