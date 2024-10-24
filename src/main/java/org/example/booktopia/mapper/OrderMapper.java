package org.example.booktopia.mapper;

import org.example.booktopia.dtos.OrderDto;
import org.example.booktopia.model.Order;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrderMapper {

    Order toEntity(OrderDto orderDto);

    @Mapping(target = "buyerId", source = "buyer.id")
    OrderDto toDto(Order order);

    List<Order> toEntity(List<OrderDto> orderDtos);

    List<OrderDto> toDto(List<Order> orders);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Order partialUpdate(
            OrderDto orderDto, @MappingTarget Order order);
}
