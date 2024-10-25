package org.example.booktopia.mapper;

import org.example.booktopia.dtos.ProductDto;
import org.example.booktopia.model.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    Product toEntity(ProductDto productDto);

    @Mapping(target = "categoryId", source = "category.id")
    @Mapping(target = "categoryName", source = "category.name")
    ProductDto toDto(Product product);

    List<Product> toEntity(List<ProductDto> productDtos);

    List<ProductDto> toDto(List<Product> products);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(
            ProductDto productDto, @MappingTarget Product product);
}