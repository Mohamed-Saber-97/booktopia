package org.example.booktopia.mapper;

import org.example.booktopia.dtos.AdminDto;
import org.example.booktopia.model.Admin;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdminMapper {
    Admin toEntity(AdminDto adminDto);

    AdminDto toDto(Admin admin);

    List<Admin> toEntity(List<AdminDto> adminDto);

    List<AdminDto> toDto(List<Admin> admin);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Admin partialUpdate(
            AdminDto adminDto, @MappingTarget Admin admin);
}