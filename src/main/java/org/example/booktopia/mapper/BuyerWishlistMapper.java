package org.example.booktopia.mapper;

import org.example.booktopia.dtos.BuyerWishlistDto;
import org.example.booktopia.model.BuyerWishlist;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BuyerWishlistMapper {

    BuyerWishlist toEntity(BuyerWishlistDto buyerWishlistDto);

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
    BuyerWishlistDto toDto(BuyerWishlist buyerWishlist);

    List<BuyerWishlist> toEntity(List<BuyerWishlistDto> buyerWishlistDtos);

    List<BuyerWishlistDto> toDto(List<BuyerWishlist> buyerWishlists);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BuyerWishlist partialUpdate(
            BuyerWishlistDto buyerWishlistDto, @MappingTarget BuyerWishlist buyerWishlist);
}
