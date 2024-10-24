package org.example.booktopia.mapper;

import org.example.booktopia.dtos.OrderProductDto;
import org.example.booktopia.model.OrderProduct;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderProductMapper {
    OrderProduct toEntity(OrderProductDto orderProductDto);

    @Mapping(target = "orderId", source = "order.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productDescription", source = "product.description")
    @Mapping(target = "productAuthor", source = "product.author")
    @Mapping(target = "productIsbn", source = "product.isbn")
    @Mapping(target = "productReleaseDate", source = "product.releaseDate")
    @Mapping(target = "productCategoryId", source = "product.category.id")
    @Mapping(target = "productImagePath", source = "product.imagePath")
    OrderProductDto toDto(OrderProduct orderProduct);

    List<OrderProduct> toEntity(List<OrderProductDto> orderProductDtos);

    List<OrderProductDto> toDto(List<OrderProduct> orderProducts);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    OrderProduct partialUpdate(
            OrderProductDto orderProductDto, @MappingTarget OrderProduct orderProduct);
}
