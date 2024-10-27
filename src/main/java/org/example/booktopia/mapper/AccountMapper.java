package org.example.booktopia.mapper;

import org.example.booktopia.dtos.AccountDto;
import org.example.booktopia.model.Account;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AddressMapper.class})
public interface AccountMapper {
    Account toEntity(AccountDto accountDto);

    AccountDto toDto(Account account);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Account partialUpdate(
            AccountDto accountDto,
            @MappingTarget Account account);
}