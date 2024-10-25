package org.example.booktopia.mapper;

import org.example.booktopia.dtos.BuyerDto;
import org.example.booktopia.model.Buyer;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface BuyerMapper {
    Buyer toEntity(BuyerDto buyerDto);

    @Mapping(target = "name", source = "account.name")
    @Mapping(target = "email", source = "account.email")
    @Mapping(target = "birthday", source = "account.birthday")
    @Mapping(target = "job", source = "account.job")
    @Mapping(target = "phoneNumber", source = "account.phoneNumber")
    @Mapping(target = "country", source = "account.address.country")
    @Mapping(target = "city", source = "account.address.city")
    @Mapping(target = "street", source = "account.address.street")
    @Mapping(target = "zipcode", source = "account.address.zipcode")
    @Mapping(target = "cartSize", expression = "java(buyer.getCartItems().size())")
    @Mapping(target = "wishlistSize", expression = "java(buyer.getProducts().size())")
    BuyerDto toDto(Buyer buyer);

    List<Buyer> toEntity(List<BuyerDto> buyerDtos);

    List<BuyerDto> toDto(List<Buyer> buyers);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Buyer partialUpdate(
            BuyerDto buyerDto, @MappingTarget Buyer buyer);
}
