package org.example.booktopia.mapper;

import org.example.booktopia.dtos.OrderDto;
import org.example.booktopia.model.Order;
import org.mapstruct.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    @Mapping(target = "id", source = "buyerId")
    @Mapping(target = "createdDate", source = "createdDate", qualifiedByName = "localDateToInstant")
    Order toEntity(OrderDto orderDto);

    @Mapping(target = "buyerId", source = "buyer.id")
    @Mapping(target = "numberOfProducts", expression = "java(order.getOrderProducts().size())")
    @Mapping(target = "createdDate", source = "createdDate", qualifiedByName = "instantToLocalDate")
    OrderDto toDto(Order order);

    List<Order> toEntity(List<OrderDto> orderDtos);

    List<OrderDto> toDto(List<Order> orders);

    @Named("instantToLocalDate")
    default LocalDate instantToLocalDate(Instant instant) {
        return instant != null ? instant.atZone(ZoneId.systemDefault()).toLocalDate() : null;
    }

    @Named("localDateToInstant")
    default Instant localDateToInstant(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay(ZoneId.systemDefault()).toInstant() : null;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", source = "buyerId")
    @Mapping(target = "createdDate", source = "createdDate", qualifiedByName = "localDateToInstant")
    Order partialUpdate(OrderDto orderDto, @MappingTarget Order order);
}