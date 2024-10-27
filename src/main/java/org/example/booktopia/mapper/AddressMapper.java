package org.example.booktopia.mapper;

import org.example.booktopia.dtos.AddressDto;
import org.example.booktopia.model.Address;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AddressMapper {
    Address toEntity(AddressDto addressDto);

    AddressDto toDto(Address address);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address partialUpdate(
            AddressDto addressDto,
            @MappingTarget Address address);
}