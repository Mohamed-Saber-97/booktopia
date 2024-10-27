package org.example.booktopia.mapper;

import org.example.booktopia.dtos.NewProductDto;
import org.example.booktopia.model.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewProductMapper {
    @Mapping(target = "category.id", source = "categoryId")
    Product toEntity(NewProductDto productDto);

    NewProductDto toDto(Product product);

    List<Product> toEntity(List<NewProductDto> productDtos);

    List<NewProductDto> toDto(List<Product> products);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(
            NewProductDto productDto, @MappingTarget Product product);
}