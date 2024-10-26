package org.example.booktopia.mapper;

import org.example.booktopia.dtos.AdminDto;
import org.example.booktopia.model.Admin;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdminMapper {
    @Mapping(source = "country", target = "account.address.country")
    @Mapping(source = "zipCode", target = "account.address.zipcode")
    @Mapping(source = "city", target = "account.address.city")
    @Mapping(source = "street", target = "account.address.street")
    @Mapping(source = "phoneNumber", target = "account.phoneNumber")
    @Mapping(source = "email", target = "account.email")
    @Mapping(source = "job", target = "account.job")
    @Mapping(source = "password", target = "account.password")
    @Mapping(source = "dob", target = "account.birthday")
    @Mapping(source = "name", target = "account.name")
    @Mapping(source = "id", target = "id")
    Admin toEntity(AdminDto adminDto);

    @InheritInverseConfiguration(name = "toEntity")
    AdminDto toDto(Admin admin);

    @InheritConfiguration(name = "toEntity")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Admin partialUpdate(
            AdminDto adminDto, @MappingTarget Admin admin);
}