package dto;

import java.math.BigDecimal;

public record BuyerDto(Long id,
                       String name,
                       String birthday,
                       String job,
                       String email,
                       BigDecimal creditLimit,
                       String phoneNumber,
                       String country,
                       String city,
                       String street,
                       String zipcode) {
}