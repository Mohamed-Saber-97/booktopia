package org.example.booktopia.mapper;

import org.example.booktopia.DTOs.CategoryDTO;
import org.example.booktopia.DTOs.ProductDTO;
import org.example.booktopia.model.Category;
import org.example.booktopia.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.i18n.LocaleContextHolder;

@Mapper(imports = {LocaleContextHolder.class})
public interface ProductMapper extends GenericMapper<Product, ProductDTO> {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
}
