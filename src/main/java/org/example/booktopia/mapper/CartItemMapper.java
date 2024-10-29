package org.example.booktopia.mapper;

import org.example.booktopia.dtos.CartItemDto;
import org.example.booktopia.model.CartItem;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CartItemMapper {

    CartItem toEntity(CartItemDto cartItemDto);

    @Mapping(target = "buyerId", source = "buyer.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "productDescription", source = "product.description")
    @Mapping(target = "productAuthor", source = "product.author")
    @Mapping(target = "productIsbn", source = "product.isbn")
    @Mapping(target = "productReleaseDate", source = "product.releaseDate")
    @Mapping(target = "productCategoryId", source = "product.category.id")
    @Mapping(target = "productImagePath", source = "product.imagePath")
    @Mapping(target = "productPrice", source = "product.price")
    CartItemDto toDto(CartItem cartItem);

    List<CartItem> toEntity(List<CartItemDto> cartItemDtos);

    List<CartItemDto> toDto(List<CartItem> cartItems);

    Set<CartItem> toEntity(Set<CartItemDto> cartItemDtos);

    Set<CartItemDto> toDto(Set<CartItem> cartItems);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CartItem partialUpdate(
            CartItemDto cartItemDto, @MappingTarget CartItem cartItem);
}